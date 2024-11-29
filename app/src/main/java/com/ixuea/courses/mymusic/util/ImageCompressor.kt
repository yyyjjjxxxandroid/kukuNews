package com.ixuea.courses.mymusic.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.concurrent.Executors

object ImageCompressor {
    // 使用共享 Handler 实例
    private val mainHandler = Handler(Looper.getMainLooper())

    // 使用 ExecutorService 执行异步任务
    private val executorService = Executors.newFixedThreadPool(4) // 可根据需要调整线程池大小
    fun compressImagesAsync(context: Context, imageUris: List<Uri>, callback: CompressionCallback) {
        for (imageUri in imageUris) {
            compressImageAsync(context, imageUri, callback)
        }
    }

    private fun compressImageAsync(context: Context, imageUri: Uri, callback: CompressionCallback) {
        executorService.submit {
            try {
                val originalFilePath = imageUri.toString()
                val compressedFilePath = compressImage(context, imageUri)
                // 在主线程中调用回调
                mainHandler.post {
                    callback.onCompressionComplete(
                        originalFilePath,
                        compressedFilePath
                    )
                }
            } catch (e: Exception) {
                // 在主线程中调用回调
                mainHandler.post { callback.onCompressionError(e) }
            }
        }
    }

    @Throws(IOException::class)
    private fun compressImage(context: Context, imageUri: Uri): String {
        // 定义最大边长为 1080 像素
        val maxSize = 1080

        // 获取 ContentResolver
        val contentResolver = context.contentResolver

        // 获取图像的输入流
        var compressedBitmap: Bitmap?
        contentResolver.openInputStream(imageUri).use { inputStream ->
            // 从输入流中加载原始位图
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeStream(inputStream, null, options)

            // 计算压缩比例
            val originalWidth = options.outWidth
            val originalHeight = options.outHeight
            var newWidth = originalWidth
            var newHeight = originalHeight

            // 调整宽高，保持比例，并确保最大边不超过 1080 像素
            if (originalWidth > originalHeight) {
                if (originalWidth > maxSize) {
                    newWidth = maxSize
                    newHeight = originalHeight * maxSize / originalWidth
                }
            } else {
                if (originalHeight > maxSize) {
                    newHeight = maxSize
                    newWidth = originalWidth * maxSize / originalHeight
                }
            }

            // 使用新宽高加载压缩后的位图
            options.inJustDecodeBounds = false
            options.inSampleSize = calculateInSampleSize(options, newWidth, newHeight)
            contentResolver.openInputStream(imageUri).use { inputStream2 ->
                compressedBitmap = BitmapFactory.decodeStream(inputStream2, null, options)
            }

            // 创建缩放后的位图
            val scaledBitmap =
                Bitmap.createScaledBitmap(compressedBitmap!!, newWidth, newHeight, true)

            // 生成唯一的文件名，使用时间戳避免文件覆盖
            val uniqueFileName: String
            val fileExtension = getFileExtension(context, imageUri)
            uniqueFileName = if ("png".equals(fileExtension, ignoreCase = true)) {
                "compressed_" + System.currentTimeMillis() + ".png"
            } else {
                "compressed_" + System.currentTimeMillis() + ".jpg"
            }

            // 创建子目录
            val cacheDir = context.externalCacheDir
            val subDir = File(cacheDir, "compressed_images")
            if (!subDir.exists()) {
                subDir.mkdirs()
            }

            // 保存压缩文件
            val compressedFile = File(subDir, uniqueFileName)
            FileOutputStream(compressedFile).use { out ->
                if ("png".equals(fileExtension, ignoreCase = true)) {
                    scaledBitmap.compress(Bitmap.CompressFormat.PNG, 6, out)
                } else {
                    scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 85, out)
                }
            }

            // 返回压缩文件的路径
            return compressedFile.absolutePath
        }
    }

    // 从 Uri 获取文件路径的方法
    //    private static String getFilePathFromUri(Context context, Uri uri) {
    //        String[] projection = {MediaStore.Images.Media.DATA};
    //        try (Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null)) {
    //            if (cursor != null && cursor.moveToFirst()) {
    //                return cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
    //            }
    //        }
    //        return null;
    //    }
    // 获取文件扩展名的方法
    private fun getFileExtension(context: Context, uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.MIME_TYPE)
        context.contentResolver.query(uri, projection, null, null, null).use { cursor ->
            if (cursor != null && cursor.moveToFirst()) {
                val mimeType =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE))
                return mimeType.substring(mimeType.lastIndexOf("/") + 1)
            }
        }
        return ""
    }

    // 计算采样率的方法
    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    // 定义回调接口
    interface CompressionCallback {
        fun onCompressionComplete(originalFilePath: String, compressedFilePath: String)
        fun onCompressionError(e: Exception)
    }
}

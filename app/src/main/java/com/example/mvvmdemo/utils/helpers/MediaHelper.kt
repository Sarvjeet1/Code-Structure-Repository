package com.example.mvvmdemo.utils.helpers

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.os.AsyncTask
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.mvvmdemo.R
import com.example.mvvmdemo.utils.constants.ApiConst
import com.example.mvvmdemo.utils.constants.AppConst
import com.example.mvvmdemo.utils.constants.MediaConst
import com.example.mvvmdemo.utils.enums.ImageType
import com.example.mvvmdemo.utils.listeners.DownloadMediaListener
import com.example.mvvmdemo.utils.listeners.VideoCompressionListener
import com.iceteck.silicompressorr.SiliCompressor
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.UCropActivity
import java.io.*
import java.math.BigDecimal
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.math.min
import kotlin.math.roundToInt

/**
 * This is the helper class which contains all the methods or functions regarding Images.
 */
object MediaHelper {

    private var TAG = javaClass.simpleName
    private var videoCompressAsyncTask: VideoCompressAsyncTask? = null
    private var downloadMediaAsyncTask: DownloadMediaAsyncTask? = null

    private var mediaPlayer: MediaPlayer? = null

    /**
     * It generates complete media url
     *
     * @param mediaPath media path
     * @return complete media url
     */
    internal fun getCompleteMediaUrl(mediaPath: String?): String? {
        if (null == mediaPath)
            return null

        return "${ApiConst.URL.BASE_MEDIA_URL}$mediaPath"
    }

    private fun getPlaceholderResId(imageType: ImageType): Int =
        when (imageType) {
//            ImageType.ANY -> R.drawable.ic_placeholder_any
            ImageType.NOTIFICATION -> R.mipmap.ic_launcher
//            ImageType.USER -> R.drawable.ic_placeholder_user
//            ImageType.PROFILE -> R.drawable.ic_placeholder_user
//            ImageType.INSURED_PHOTO -> R.drawable.ic_placeholder_any
//            ImageType.CHAT_MEDIA -> R.drawable.ic_placeholder_chat_media
//            ImageType.ADJUSTER -> R.drawable.ic_adjuster
//            ImageType.TIMBER_WARRIOR -> R.drawable.ic_tw
            else -> R.mipmap.ic_launcher
        }

    /**
     * Here loading a image to the imageView using Glide
     *
     * @param context context
     * @param imageView imageView to which an image is set
     * @param tempUrl temporary image url to be loaded
     * @param finalUrl final image url to be loaded
     * @param imageType type of the image
     */
    internal fun loadImage(
        context: Context,
        imageView: ImageView,
        tempUrl: String?,
        finalUrl: String?,
        imageType: ImageType
    ) {
        val placeholderResId: Int = getPlaceholderResId(imageType = imageType)
        val placeholderDrawable: Drawable? = ContextCompat.getDrawable(context, placeholderResId)

        var updatedTempUrl = tempUrl
        var updatedFinalUrl = finalUrl

        if (updatedFinalUrl.isNullOrBlank()) {
            updatedFinalUrl = updatedTempUrl
            updatedTempUrl = null
        }

        when {
            updatedTempUrl.isNullOrBlank() ->
                loadFinalImage(
                    context = context,
                    imageView = imageView,
                    finalUrl = updatedFinalUrl,
                    placeholderResId = placeholderResId,
                    placeholderDrawable = placeholderDrawable
                )

            !updatedTempUrl.isNullOrBlank() && null != placeholderDrawable ->
                Picasso.get()
                    .load(updatedTempUrl)
                    .placeholder(placeholderDrawable)
                    .error(placeholderDrawable)
                    .into(imageView, object : Callback {
                        override fun onSuccess() {
                            loadFinalImage(
                                context = context,
                                imageView = imageView,
                                finalUrl = updatedFinalUrl,
                                placeholderResId = placeholderResId,
                                placeholderDrawable = imageView.drawable
                            )
                        }

                        override fun onError(e: java.lang.Exception?) {
                        }
                    })

            !updatedTempUrl.isNullOrBlank() && null == placeholderDrawable ->
                Picasso.get()
                    .load(updatedTempUrl)
                    .into(imageView, object : Callback {
                        override fun onSuccess() {
                            loadFinalImage(
                                context = context,
                                imageView = imageView,
                                finalUrl = updatedFinalUrl,
                                placeholderResId = placeholderResId,
                                placeholderDrawable = imageView.drawable
                            )
                        }

                        override fun onError(e: java.lang.Exception?) {
                        }
                    })
        }
    }

    /**
     * Here loading a image to the imageView using Picasso
     *
     * @param context context
     * @param imageView imageView to which an image is set
     * @param bitmap image bitmap to be loaded
     * @param imageType type of the image
     */
    internal fun loadImage(
        context: Context,
        imageView: ImageView,
        bitmap: Bitmap?,
        imageType: ImageType
    ) {
        val placeholderResId: Int = getPlaceholderResId(imageType = imageType)

        when {
            bitmap != null ->
                imageView.setImageBitmap(bitmap)

            else ->
                Picasso.get()
                    .load(placeholderResId)
                    .into(imageView)
        }
    }

    /**
     * Here loading the final image to the imageView using Picasso
     *
     * @param context context
     * @param imageView imageView to which an image is set
     * @param finalUrl final image url to be loaded
     * @param placeholderResId drawable's resource id for placeholder
     * @param placeholderDrawable drawable for placeholder
     */
    private fun loadFinalImage(
        context: Context,
        imageView: ImageView,
        finalUrl: String?,
        placeholderResId: Int,
        placeholderDrawable: Drawable?
    ) {
        when {
            !finalUrl.isNullOrBlank() && null != placeholderDrawable ->
                Picasso.get()
                    .load(finalUrl)
                    .placeholder(placeholderDrawable)
                    .error(placeholderDrawable)
                    .into(imageView)

            !finalUrl.isNullOrBlank() && null == placeholderDrawable ->
                Picasso.get()
                    .load(finalUrl)
                    .into(imageView)

            finalUrl.isNullOrBlank() && null != placeholderDrawable ->
                Picasso.get()
                    .load(placeholderResId)
                    .placeholder(placeholderDrawable)
                    .error(placeholderDrawable)
                    .into(imageView)

            finalUrl.isNullOrBlank() && null == placeholderDrawable ->
                Picasso.get()
                    .load(placeholderResId)
                    .into(imageView)
        }
    }

    /**
     *  Here creating an image directory
     *
     *  @param context
     *  @return
     */
    internal fun getImageTempDirectory(context: Context): File? {
        val storageDir: File =
            File(
                "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/${
                    context.getString(
                        R.string.app_name
                    )
                }"
            )

        try {
            // Make sure the directory exists.
            storageDir.mkdirs()
        } catch (e: IOException) {
            // Error occurred while creating the File
            LogHelper.error(tag = TAG, msg = "getImageTempDirectory: mkdirs error- ${e.message}")
        }

        return if (storageDir.isDirectory)
            storageDir
        else
            null
    }

    /**
     *  Here creating an image file
     *
     *  @param context
     *  @return
     */
    internal fun getImageTempFile(context: Context): File? {
        var file: File? = null
        val storageDir: File? = getImageTempDirectory(context)
        val imageFileName: String =
            "${context.getString(R.string.app_name)}_${DateTimeHelper.getCurrentTimestampInMilliseconds()}"

        if (storageDir == null)
            return null

        try {
            file =
                File.createTempFile(
                    imageFileName, // prefix
                    MediaConst.KEY.EXTENSION_JPG, // suffix
                    storageDir // directory
                )
        } catch (e: IOException) {
            // Error occurred while creating the File
            LogHelper.error(tag = TAG, msg = "getTempFile: createTempFile error- ${e.message}")
        }

        return file
    }

    /**
     *  Here creating an video directory
     *
     *  @param context
     *  @return
     */
    internal fun getVideoTempDirectory(context: Context): File? {
        val storageDir: File =
            File(
                "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)}/${
                    context.getString(
                        R.string.app_name
                    )
                }"
            )

        try {
            // Make sure the directory exists.
            storageDir.mkdirs()
        } catch (e: IOException) {
            // Error occurred while creating the File
            LogHelper.error(tag = TAG, msg = "getVideoTempDirectory: mkdirs error- ${e.message}")
        }

        return if (storageDir.isDirectory)
            storageDir
        else
            null
    }

    /**
     *  Here creating an video file
     *
     *  @param context
     *  @return
     */
    internal fun getVideoTempFile(context: Context): File? {
        var file: File? = null
        val storageDir: File? = getVideoTempDirectory(context)
        val imageFileName: String =
            "${context.getString(R.string.app_name)}_${DateTimeHelper.getCurrentTimestampInMilliseconds()}"

        if (storageDir == null)
            return null

        try {
            file =
                File.createTempFile(
                    imageFileName, // prefix
                    MediaConst.KEY.EXTENSION_MP4, // suffix
                    storageDir // directory
                )
        } catch (e: IOException) {
            // Error occurred while creating the File
            LogHelper.error(tag = TAG, msg = "getVideoTempFile: createTempFile error- ${e.message}")
        }

        return file
    }

    /**
     * Tt is called to crop captured image from activity
     *
     * @param activity
     * @param sourceUri
     * @param destinationUri
     * @param aspectRatioX
     * @param aspectRatioY
     * @param maxSize
     */
    internal fun openImageCropActivity(
        activity: Activity,
        sourceUri: Uri,
        destinationUri: Uri,
        aspectRatioX: Float,
        aspectRatioY: Float,
        maxSize: Int
    ) {
        var uCrop: UCrop = UCrop.of(sourceUri, destinationUri)

        uCrop = basisImageCropConfig(
            context = activity,
            uCrop = uCrop,
            aspectRatioX = aspectRatioX,
            aspectRatioY = aspectRatioY,
            maxSize = maxSize
        )
        uCrop.start(activity)
    }

    /**
     * Tt is called to crop captured image from fragment
     *
     * @param fragment
     * @param sourceUri
     * @param destinationUri
     * @param aspectRatioX
     * @param aspectRatioY
     * @param maxSize
     */
    internal fun openImageCropActivity(
        context: Context,
        fragment: Fragment,
        sourceUri: Uri,
        destinationUri: Uri,
        aspectRatioX: Float,
        aspectRatioY: Float,
        maxSize: Int
    ) {
        var uCrop: UCrop = UCrop.of(sourceUri, destinationUri)

        uCrop = basisImageCropConfig(
            context = context,
            uCrop = uCrop,
            aspectRatioX = aspectRatioX,
            aspectRatioY = aspectRatioY,
            maxSize = maxSize
        )
//        uCrop.start(context, fragment)
    }

    /**
     * In most cases you need only to set crop aspect ration and max size for resulting image.
     * Sometimes you want to adjust more options, it's done via [com.yalantis.ucrop.UCrop.Options] class.
     *
     * @param context
     * @param uCrop - ucrop builder instance
     * @param aspectRatioX
     * @param aspectRatioY
     * @param maxSize
     *
     * @return ucrop builder instance
     */
    private fun basisImageCropConfig(
        context: Context,
        uCrop: UCrop,
        aspectRatioX: Float,
        aspectRatioY: Float,
        maxSize: Int
    ): UCrop {
        val options = UCrop.Options()

        options.apply {
            withAspectRatio(aspectRatioX, aspectRatioY)

            // If you want to configure how gestures work for all UCropActivity tabs
            setCompressionFormat(Bitmap.CompressFormat.JPEG)
            setCompressionQuality(100)
            setMaxBitmapSize(maxSize)
            withMaxResultSize(maxSize, maxSize)
            setHideBottomControls(false)
            setFreeStyleCropEnabled(false)
            setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL)

            setMaxScaleMultiplier(5f)
            setImageToCropBoundsAnimDuration(666)
            setCircleDimmedLayer(false)
            setShowCropFrame(false)
            setCropGridStrokeWidth(1)
            setCropGridColor(Color.WHITE)
            setCropGridColumnCount(3)
            setCropGridRowCount(3)

            // Color palette
            setToolbarCancelDrawable(R.drawable.ic_arrow_left_gravity_start_white)
            setStatusBarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
            setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
        }

        return uCrop.withOptions(options)
    }

    /**
     *  Here compressing quality and resizing the image
     *
     *  @param activity
     *  @param imageUri
     *  @param maxSize
     *  @param compressionQuality
     */
    internal fun resizeImage(
        activity: Activity,
        imageUri: Uri?,
        maxSize: Int,
        compressionQuality: Int
    ): File? {
        if (null == imageUri)
            return null

        var bitmap: Bitmap
        try {
            bitmap = MediaStore.Images.Media.getBitmap(activity.contentResolver, imageUri)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

        return resizeImage(
            activity = activity,
            bitmap = bitmap,
            maxSize = maxSize,
            compressionQuality = compressionQuality
        )
    }

    /**
     *  Here compressing quality and resizing the image
     *
     *  @param activity
     *  @param bitmap
     *  @param maxSize
     *  @param compressionQuality
     */
    internal fun resizeImage(
        activity: Activity,
        bitmap: Bitmap?,
        maxSize: Int,
        compressionQuality: Int
    ): File? {
        if (null == bitmap)
            return null

        val newBitmap: Bitmap = resizeBitmap(bitmap = bitmap, maxSize = maxSize)

        val outStream = ByteArrayOutputStream()
        // compress to the format you want, JPEG, PNG...
        // 70 is the 0-100 quality percentage
        newBitmap.compress(
            Bitmap.CompressFormat.JPEG,
            compressionQuality,
            outStream
        )

        // we save the file, at least until we have made use of it
        val f = getImageTempFile(context = activity)
        //write the bytes in file
        val fo = FileOutputStream(f)
        fo.write(outStream.toByteArray())
        // remember close de FileOutput
        fo.close()

        return f
    }

    private fun resizeBitmap(bitmap: Bitmap, maxSize: Int): Bitmap {
        var newBitmap: Bitmap = bitmap

        if (bitmap.width > maxSize || bitmap.height > maxSize) {
            val ratio = min(
                a = maxSize.toDouble() / bitmap.width.toDouble(),
                b = maxSize.toDouble() / bitmap.height.toDouble()
            )

            val width = (ratio * bitmap.width).roundToInt()
            val height = (ratio * bitmap.height).roundToInt()

            newBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true)
            bitmap.recycle()
        }

        return newBitmap
    }

    /**
     *  Here creating a cover image from video
     *
     *  @param context
     *  @param videoUri
     *  @return video's cover image in bitmap format
     */
    internal fun getCoverImageFromVideo(context: Context, videoUri: Uri): Bitmap? {
        val retriever = MediaMetadataRetriever()
        var bitmap: Bitmap? = null

        LogHelper.debug(TAG, "getCoverImageFromVideo: videoUri- $videoUri")

        try {
            retriever.apply {
                setDataSource(context, videoUri)

                frameAtTime?.apply {
//                    bitmap = resizeBitmap(this, maxSize)
                    bitmap = this
                }
            }
        } catch (e: Exception) {
            LogHelper.error(TAG, "getCoverImageFromVideo: ${e.printStackTrace()}")
        } finally {
            retriever.release()
        }

        return bitmap
    }

    internal fun getScreenShot(view: View): Bitmap? {
        var bitmap: Bitmap? = null

        view.rootView.apply {
            isDrawingCacheEnabled = true
            bitmap = Bitmap.createBitmap(drawingCache)
            isDrawingCacheEnabled = false
        }

        return bitmap
    }

    /**
     *  Here compressing video
     */
    internal fun compressVideo(
        context: Context,
        inputFile: File,
        outputDirectory: File,
        videoCompressionListener: VideoCompressionListener
    ) {
        videoCompressAsyncTask?.apply {
            if (!isCancelled) cancel(true)
        }

        videoCompressAsyncTask = VideoCompressAsyncTask(context, videoCompressionListener).apply {
            execute(inputFile.path, outputDirectory.path)
        }
    }

    private class VideoCompressAsyncTask(
        context: Context,
        listener: VideoCompressionListener
    ) : AsyncTask<String?, String?, String?>() {

        private var context: Context? = null
        private var listener: VideoCompressionListener? = null

        init {
            this.context = context
            this.listener = listener
        }

        override fun doInBackground(vararg paths: String?): String? {
            Log.d(TAG, "VideoCompressAsyncTask: doInBackground")
            if (paths[0] == null || paths[1] == null)
                return null

            val imageFile = File(paths[0]!!)
            val length = (imageFile.length() / 1024f) // Size in KB
            val size: String =
                if (length >= 1024) {
                    val s = ((length / 1024f)).toBigDecimal()
                    val mediaSize = s.setScale(AppConst.NUMBER.TWO, BigDecimal.ROUND_HALF_EVEN)
                    "$mediaSize MB"
                } else {
                    val s = (length).toBigDecimal()
                    val mediaSize = s.setScale(AppConst.NUMBER.TWO, BigDecimal.ROUND_HALF_EVEN)
                    "$mediaSize KB"
                }

            Log.d(TAG, "VideoCompressAsyncTask: original_size- $size")

            return SiliCompressor.with(context).compressVideo(paths[0], paths[1])
        }

        override fun onPostExecute(compressedFilePath: String?) {
            super.onPostExecute(compressedFilePath)
            Log.d(TAG, "VideoCompressAsyncTask: onPostExecute")

            if (compressedFilePath == null) {
                listener?.onVideoCompressionFailure()
                return
            }

            val imageFile = File(compressedFilePath)
            val length = (imageFile.length() / 1024f) // Size in KB
            val size: String =
                if (length >= 1024) {
                    val s = ((length / 1024f)).toBigDecimal()
                    val mediaSize = s.setScale(AppConst.NUMBER.TWO, BigDecimal.ROUND_HALF_EVEN)
                    "$mediaSize MB"
                } else {
                    val s = (length).toBigDecimal()
                    val mediaSize = s.setScale(AppConst.NUMBER.TWO, BigDecimal.ROUND_HALF_EVEN)
                    "$mediaSize KB"
                }

            Log.d(TAG, "VideoCompressAsyncTask: compressed_size- $size")

            listener?.onVideoCompressionSuccess(compressedVideoFile = imageFile, mediaSize = size)

            context = null
            listener = null
            videoCompressAsyncTask = null
        }

        override fun onCancelled(result: String?) {
            super.onCancelled(result)
            Log.d(TAG, "VideoCompressAsyncTask: onCancelled")

            listener?.onVideoCompressionFailure()

            context = null
            listener = null
            videoCompressAsyncTask = null
        }
    }

    internal fun findVideoDuration(compressedVideoFile: String): String {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(ApiConst.URL.BASE_MEDIA_URL + compressedVideoFile, HashMap())
        val time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        val timeInMillisec = time!!.toLong()
        retriever.release()
        return convertMillieToHMmSs(timeInMillisec)!! //use this duration
    }

    private fun convertMillieToHMmSs(millie: Long): String? {
        val seconds = millie / 1000
        val second = seconds % 60
        val minute = seconds / 60 % 60
        val hour = seconds / (60 * 60) % 24
        val result = ""
        return if (hour > 0) {
            String.format("%02d:%02d:%02d", hour, minute, second)
        } else {
            String.format("%02d:%02d", minute, second)
        }
    }

    internal fun getMediaSize(photoFile: File?): String {
        var size = ""
        if (photoFile != null) {
            val length = (photoFile.length() / 1024f) // Size in KB
            size =
                if (length >= 1024) {
                    val s = ((length / 1024f)).toBigDecimal()
                    Log.d(TAG, "Media size 1--- :: $s")
                    val mediaSize = s.setScale(AppConst.NUMBER.TWO, BigDecimal.ROUND_HALF_EVEN)
                    "$mediaSize MB"
                } else {
                    val s = (length).toBigDecimal()
                    Log.d(TAG, "Media size 2--- :: $s")
                    val mediaSize = s.setScale(AppConst.NUMBER.TWO, BigDecimal.ROUND_HALF_EVEN)
                    "$mediaSize KB"
                }
            Log.d(TAG, "Media size 3--- :: $size")
        }
        return size
    }

    /**
     *  Here downloading media
     */
    internal fun downloadMedia(
        context: Context,
        mediaUrl: String,
        outputDirectory: File,
        downloadMediaListener: DownloadMediaListener
    ) {

        downloadMediaAsyncTask = DownloadMediaAsyncTask(context, downloadMediaListener).apply {
            execute(mediaUrl, outputDirectory.path)
        }
    }

    private class DownloadMediaAsyncTask(
        context: Context,
        listener: DownloadMediaListener
    ) : AsyncTask<String?, String?, String?>() {

        private var context: Context? = null
        private var listener: DownloadMediaListener? = null

        init {
            this.context = context
            this.listener = listener
        }

        override fun doInBackground(vararg paths: String?): String? {
            Log.d(TAG, "DownloadMediaAsyncTask: doInBackground")

            val mediaFile = File(paths[1]!!)
            val length = mediaFile.length() / 1024f // Size in KB
            val size: String =
                if (length >= 1024) (length / 1024f).toString() + " MB" else "$length KB"

            Log.d(TAG, "DownloadMediaAsyncTask: media size before write- $size")


            if (paths[0] == null || paths[1] == null)
                return null

            var input: InputStream? = null
            var output: OutputStream? = null
            var connection: HttpURLConnection? = null
            try {
                val url = URL(paths[0])
                connection = url.openConnection() as HttpURLConnection
                connection.connect()
                if (connection!!.responseCode != HttpURLConnection.HTTP_OK) {
                    return ("Server returned HTTP " + connection.responseCode
                            + " " + connection.responseMessage)
                }
                val fileLength = connection.contentLength
                input = connection.inputStream

                output = FileOutputStream(paths[1])
                val data = ByteArray(4096)
                var total: Long = 0
                var count: Int
                while (input.read(data).also { count = it } != -1) {
                    if (isCancelled) {
                        input.close()
                        return null
                    }
                    total += count.toLong()
                    if (fileLength > 0) publishProgress(
                        (total * 100 / fileLength).toInt().toString()
                    )
                    output.write(data, 0, count)
                }
            } catch (e: java.lang.Exception) {
                return e.toString()
            } finally {
                try {
                    output?.close()
                    input?.close()
                } catch (ignored: IOException) {
                }
                connection?.disconnect()
            }

            return paths[1]
        }

        override fun onPostExecute(downloadedMediaPath: String?) {
            super.onPostExecute(downloadedMediaPath)
            Log.d(TAG, "DownloadMediaAsyncTask: onPostExecute")

            if (downloadedMediaPath == null) {
                listener?.onDownloadMediaFailure()
                return
            }

            val mediaFile = File(downloadedMediaPath)
            val length = mediaFile.length() / 1024f // Size in KB
            val size: String =
                if (length >= 1024) (length / 1024f).toString() + " MB" else "$length KB"

            Log.d(TAG, "DownloadMediaAsyncTask: media size after write- $size")

            listener?.onDownloadMediaSuccess(
                downloadedMediaPath = Uri.fromFile(
                    File(
                        downloadedMediaPath
                    )
                ).toString()
            )

            context = null
            listener = null
            downloadMediaAsyncTask = null
        }
    }

    internal fun startPlayingSound(context: Context) {
        if (mediaPlayer == null)
            initPlayingSound(context = context)

        mediaPlayer?.start()
    }

    internal fun stopPlayingSound() {
        mediaPlayer?.apply {
            stop()
            release()
        }

        mediaPlayer = null
    }

    private fun initPlayingSound(context: Context) {
//        mediaPlayer = MediaPlayer.create(context, R.raw.ringing)

        mediaPlayer?.apply {
            isLooping = true
        }
    }

    internal fun destroyObjects() {
        stopPlayingSound()
    }
}
package com.example.mvvmdemo.utils.helpers

import android.app.DatePickerDialog
import com.example.mvvmdemo.models.DateSelectedModel
import com.example.mvvmdemo.utils.enums.DialogEventType
import com.example.mvvmdemo.utils.enums.ImageType
import com.example.mvvmdemo.utils.listeners.DialogListener
import com.example.mvvmdemo.view.dialog.*
import com.example.mvvmdemo.view.view.activities.base.BaseActivity

/**
 * This is the helper class which contains all the methods or functions regarding Dialogs.
 */
object DialogHelper {
    private var TAG = javaClass.simpleName

    private var imageDialog: ImageDialog? = null
    private var imagePickerDialog: ImagePickerDialog? = null
    private var imageVideoPickerDialog: ImageVideoPickerDialog? = null
    private var loadingDialog: LoadingDialog? = null
    private var positiveAlertDialog: PositiveAlertDialog? = null
    private var positiveNegativeAlertDialog: PositiveNegativeAlertDialog? = null
    private var datePickerDialog: DatePickerDialog? = null
    private var takeOrUploadPhotosNotificationDialog: TakeOrUploadPhotosNotificationDialog? = null

    /**
     * It shows Image Dialog
     *
     * @param baseActivity
     * @param imageUrl url of original image
     * @param imageThumbUrl url of thumb image
     * @param imageType
     */
    internal fun showImageDialog(
        baseActivity: BaseActivity?,
        imageUrl: String?,
        imageThumbUrl: String?,
        imageType: ImageType
    ) {
        if (baseActivity != null) {
            hideImageDialog()

            imageDialog = ImageDialog(
                baseActivity = baseActivity,
                imageUrl = imageUrl,
                imageThumbUrl = imageThumbUrl,
                imageType = imageType
            )

            if (baseActivity.isActive())
                imageDialog?.show()
        }
    }

    /**
     * It hides Image Dialog
     */
    private fun hideImageDialog() {
        if (null != imageDialog) {
            if (imageDialog!!.isShowing)
                imageDialog!!.dismiss()

            imageDialog = null
        }
    }

    /**
     * It shows ImagePickerDialog
     *
     * @param baseActivity
     * @param requestCode
     * @param listener
     */
    internal fun showImagePickerDialog(
        baseActivity: BaseActivity?,
        requestCode: Int,
        shouldShowDelete: Boolean,
        listener: DialogListener
    ) {
        if (baseActivity != null) {
            hideImagePickerDialog()

            imagePickerDialog = ImagePickerDialog(
                baseActivity = baseActivity,
                requestCode = requestCode,
                shouldShowDelete = shouldShowDelete,
                listener = listener
            )

            if (baseActivity.isActive())
                imagePickerDialog?.show()
        }
    }

    /**
     * It hides ImagePickerDialog Dialog
     */
    private fun hideImagePickerDialog() {
        if (null != imagePickerDialog) {
            if (imagePickerDialog!!.isShowing)
                imagePickerDialog!!.dismiss()

            imagePickerDialog = null
        }
    }

    /**
     * It shows ImageVideoPickerDialog
     *
     * @param baseActivity
     * @param requestCode
     * @param listener
     */
    internal fun showImageVideoPickerDialog(
        baseActivity: BaseActivity?,
        requestCode: Int,
        listener: DialogListener
    ) {
        if (baseActivity != null) {
            hideImageVideoPickerDialog()

            imageVideoPickerDialog = ImageVideoPickerDialog(
                baseActivity = baseActivity,
                requestCode = requestCode,
                listener = listener
            )

            if (baseActivity.isActive())
                imageVideoPickerDialog?.show()
        }
    }

    /**
     * It hides ImageVideoPickerDialog Dialog
     */
    private fun hideImageVideoPickerDialog() {
        if (null != imageVideoPickerDialog) {
            if (imageVideoPickerDialog!!.isShowing)
                imageVideoPickerDialog!!.dismiss()

            imageVideoPickerDialog = null
        }
    }

    /**
     * It shows/hides Loading Dialog
     *
     * @param baseActivity
     * @param status it checks and shows/hides the Loading Dialog
     * @param message
     */
    internal fun showHideLoadingDialog(
        baseActivity: BaseActivity?,
        status: Boolean,
        message: String?
    ) {
        if (baseActivity != null) {
            hideLoadingDialog()

            if (status) {
                loadingDialog = LoadingDialog(baseActivity = baseActivity, message = message)

                if (baseActivity.isActive())
                    loadingDialog?.show()
            }
        }
    }

    /**
     * It hides Loading Dialog
     */
    private fun hideLoadingDialog() {
        if (null != loadingDialog) {
            if (loadingDialog!!.isShowing)
                loadingDialog!!.dismiss()

            loadingDialog = null
        }
    }

    /**
     * It shows PositiveAlert Dialog
     *
     * @param baseActivity
     * @param requestCode
     * @param forceLogout
     * @param title
     * @param message
     * @param positiveButtonText
     * @param listener
     */
    internal fun showPositiveAlertDialog(
        baseActivity: BaseActivity?,
        requestCode: Int,
        forceLogout: Boolean,
        title: String?,
        message: String?,
        positiveButtonText: String,
        listener: DialogListener?
    ) {
        if (baseActivity != null) {
            hidePositiveAlertDialog()

            positiveAlertDialog = PositiveAlertDialog(
                baseActivity = baseActivity,
                requestCode = requestCode,
                forceLogout = forceLogout,
                title = title,
                message = message,
                positiveButtonText = positiveButtonText,
                listener = listener
            )

            if (baseActivity.isActive())
                positiveAlertDialog?.show()
        }
    }

    /**
     * It hides OkAlert Dialog
     */
    private fun hidePositiveAlertDialog() {
        if (null != positiveAlertDialog) {
            if (positiveAlertDialog!!.isShowing)
                positiveAlertDialog!!.dismiss()

            positiveAlertDialog = null
        }
    }

    /**
     * It shows PositiveNegativeAlert Dialog
     *
     * @param baseActivity
     * @param requestCode
     * @param title
     * @param message
     * @param positiveButtonText
     * @param negativeButtonText
     * @param data
     * @param listener
     */
    internal fun showPositiveNegativeAlertDialog(
        baseActivity: BaseActivity?,
        requestCode: Int,
        title: String?,
        message: String?,
        positiveButtonText: String,
        negativeButtonText: String,
        data: Any?,
        listener: DialogListener
    ) {
        if (baseActivity != null) {
            hidePositiveNegativeAlertDialog()

            positiveNegativeAlertDialog = PositiveNegativeAlertDialog(
                baseActivity = baseActivity,
                requestCode = requestCode,
                title = title,
                message = message,
                positiveButtonText = positiveButtonText,
                negativeButtonText = negativeButtonText,
                data = data,
                listener = listener
            )

            if (baseActivity.isActive())
                positiveNegativeAlertDialog?.show()
        }
    }

    /**
     * It hides YesNoAlert Dialog
     */
    private fun hidePositiveNegativeAlertDialog() {
        if (null != positiveNegativeAlertDialog) {
            if (positiveNegativeAlertDialog!!.isShowing)
                positiveNegativeAlertDialog!!.dismiss()

            positiveNegativeAlertDialog = null
        }
    }

    /**
     * It shows DatePicker Dialog
     *
     * @param context
     * @param requestCode
     * @param listener
     */
    internal fun showDatePickerDialog(
        context: BaseActivity?,
        requestCode: Int,
        listener: DialogListener
    ) {
        if (context != null) {
            hideDatePickerDialog()

            datePickerDialog = DatePickerDialog(
                context,
                { view, year, monthOfYear, dayOfMonth ->
                    listener.onDialogEventListener(
                        dialogEventType = DialogEventType.DATE_SELECTED,
                        requestCode = requestCode,
                        model = DateSelectedModel(
                            dayOfMonth = dayOfMonth,
                            monthOfYear = monthOfYear + 1,
                            year = year
                        )
                    )

                    hideDatePickerDialog()
                },
                DateTimeHelper.getCurrentYear(),
                DateTimeHelper.getCurrentMonth(),
                DateTimeHelper.getCurrentDay()
            ).apply {
                datePicker.minDate = DateTimeHelper.getCurrentTimestampInMilliseconds()
            }

            datePickerDialog?.show()
        }
    }

    /**
     * It hides DatePicker Dialog
     */
    private fun hideDatePickerDialog() {
        if (null != datePickerDialog) {
            if (datePickerDialog!!.isShowing)
                datePickerDialog!!.dismiss()

            datePickerDialog = null
        }
    }

    /**
     * It shows TakeOrUploadPhotosNotification Dialog
     *
     * @param baseActivity
     * @param requestCode
     * @param listener
     */
    internal fun showTakeOrUploadPhotosNotificationDialog(
        baseActivity: BaseActivity?,
        requestCode: Int,
        listener: DialogListener
    ) {
        if (baseActivity != null) {
            hideTakeOrUploadPhotosNotificationDialog()

            takeOrUploadPhotosNotificationDialog = TakeOrUploadPhotosNotificationDialog(
                baseActivity = baseActivity,
                requestCode = requestCode,
                listener = listener
            )

            if (baseActivity.isActive())
                takeOrUploadPhotosNotificationDialog?.show()
        }
    }

    /**
     * It hides TakeOrUploadPhotosNotification Dialog
     */
    private fun hideTakeOrUploadPhotosNotificationDialog() {
        if (null != takeOrUploadPhotosNotificationDialog) {
            if (takeOrUploadPhotosNotificationDialog!!.isShowing)
                takeOrUploadPhotosNotificationDialog!!.dismiss()

            takeOrUploadPhotosNotificationDialog = null
        }
    }

    /**
     * It hides all the dialogs
     */
    internal fun hideAllDialogs() {
        hideImageDialog()
        hideImagePickerDialog()
        hideImageVideoPickerDialog()
        hideLoadingDialog()
        hidePositiveAlertDialog()
        hidePositiveNegativeAlertDialog()
        hideDatePickerDialog()
        hideTakeOrUploadPhotosNotificationDialog()
    }
}
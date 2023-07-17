package com.example.mvvmdemo.view.viewholder

import android.view.View
import com.example.mvvmdemo.R
import com.example.mvvmdemo.databinding.ItemTempBinding
import com.example.mvvmdemo.models.response.TempResponseModel
import com.example.mvvmdemo.utils.helpers.AppHelper
import com.example.mvvmdemo.utils.listeners.RecyclerViewItemListener
import com.example.mvvmdemo.utils.enums.ItemClickType
import com.example.mvvmdemo.view.viewholder.base.BaseViewHolder

/**
 * This is the viewHolder class used by NotificationAdapter for showing view with notification data
 *
 * @param itemView view in which data is set from NotificationListingResponseModel
 * @param listener
 */
class TempViewHolder(
    private val binding: ItemTempBinding,
    private val listener: RecyclerViewItemListener
) : BaseViewHolder(
    binding = binding,
    listener = listener
) {

    private var model: TempResponseModel? = null

    /**
     * Click event on views handles here
     */
    private val clickListener: View.OnClickListener = View.OnClickListener { view ->
        if (AppHelper.shouldProceedClick())
            when (view.id) {
                R.id.cvRoot -> clickedDetail()
            }
    }

    init {
        itemView.apply {
            binding.cvRoot.setOnClickListener(clickListener)
        }
    }

    internal fun bind(model: TempResponseModel) {
        this.model = model

        this.model?.apply {
//            updateTitle(title = title)
//            updateDescription(description = body)
//            updateTime(time = created)

//            updateSubTitle(subTitle = data?.title2)
//            updateReadUnreadStatus()
        }

    }

    /**
     * Here is updating the title
     */
    private fun updateTitle(title: String?) {
        itemView.apply {
            title?.apply {
//                tvNotificationTitle.text = this
            }
        }
    }

    /**
     * Here is updating the sub title
     */
    private fun updateSubTitle(subTitle: String?) {
        itemView.apply {
            subTitle?.apply {
//                tvNotificationSubtitle.text = this
            }
        }
    }

    /**
     * Here is updating the description
     */
    private fun updateDescription(description: String?) {
        itemView.apply {
            description?.apply {
//                tvNotificationDescription.text = this
            }
        }
    }

    /**
     * Here is updating the time
     */
    private fun updateTime(time: Long?) {
        itemView.apply {
            time?.apply {
//                tvTime.text = DateTimeHelper.formatDateTimeAgo(
//                    context = context,
//                    milliseconds = DateTimeHelper.convertSecondsToMilliseconds(seconds = time)
//                )

            }
        }
    }

//    /**
//     * Here is updating the color of read and unread notification
//     */
//    private fun updateReadUnreadStatus() {
//        itemView.apply {
//            if (model?.isViewed == AppConst.VALUE.FALSE) {
//                clRoot.background =
//                    ContextCompat.getDrawable(context, R.drawable.bg_gray_light_corner_3)
//            } else {
//                clRoot.background =
//                    ContextCompat.getDrawable(context, R.drawable.bg_white_light_corner_3)
//            }
//        }
//    }

    /**
     * It is called when user click on detail view
     */
    private fun clickedDetail() {
        itemView.apply {
            listener.onRecyclerViewItemClick(
                itemClickType = ItemClickType.SELECT_OPTION,
                model = model,
                position = layoutPosition,
                view = itemView
            )
        }
    }
}
package com.faltenreich.skeletonlayout.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.skeletonlayout.SkeletonLayout
import com.faltenreich.skeletonlayout.createSkeleton
import com.faltenreich.skeletonlayout.views

internal class SkeletonRecyclerViewAdapter(
    @LayoutRes private val layoutResId: Int,
    private val itemCount: Int,
    @ColorInt private val maskColor: Int,
    private val cornerRadius: Float,
    private val showShimmer: Boolean,
    @ColorInt private val shimmerColor: Int,
    private val shimmerDurationInMillis: Long,
    private val createSkeleton: Boolean
) : RecyclerView.Adapter<SkeletonRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkeletonRecyclerViewHolder {
        val originView = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)

        val layout = if (this.createSkeleton) {
            val skeleton = originView.createSkeleton(maskColor, cornerRadius, showShimmer, shimmerColor, shimmerDurationInMillis) as SkeletonLayout
            skeleton.layoutParams = originView.layoutParams
            skeleton.showSkeleton()
            skeleton
        } else {
            this.recursiveShowSkeleton(originView)
            originView
        }

        return SkeletonRecyclerViewHolder(layout)
    }

    private fun recursiveShowSkeleton(root: View) {
        if (root is SkeletonLayout) {
            root.showSkeleton()
            return
        }

        if (root is ViewGroup) {
            root.views().forEach { recursiveShowSkeleton(it) }
        }
    }

    override fun onBindViewHolder(holder: SkeletonRecyclerViewHolder, position: Int) = Unit

    override fun getItemCount() = itemCount
}
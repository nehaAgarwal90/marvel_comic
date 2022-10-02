//package com.example.marvel_comic.presentation.adapter
//
//import android.content.Context
//import android.content.Intent
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.example.marvel_comic.R
//import com.example.marvel_comic.data.model.ColorItem
//import com.example.marvel_comic.databinding.ListItemColorBinding
//import com.example.marvel_comic.presentation.activity.ColorInfoActivity
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.FlowPreview
//
//
//class ColorAdapter() : ListAdapter<ColorItem, ColorAdapter.ColorViewHolder>(DiffCallback) {
//    inner class ColorViewHolder(private val binding: ListItemColorBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(model: ColorItem, context: Context) {
//            binding.apply {
//                itemView.setBackgroundColor(model.colorInt)
//                itemView.setOnClickListener {
//                    startColorDetailsActivity(model)
//                }
//                executePendingBindings()
//            }
//        }
//
//        fun startColorDetailsActivity(colorItem: ColorItem) {
//            val intent = Intent(itemView.context, ColorInfoActivity::class.java)
//
//            intent.putExtra("hexcode", colorItem.colorInt)
//            itemView.context.startActivity(intent)
//        }
//    }
//
//    object DiffCallback : DiffUtil.ItemCallback<ColorItem>() {
//        override fun areItemsTheSame(
//            oldItem: ColorItem,
//            newItem: ColorItem
//        ): Boolean {
//            return oldItem == newItem
//        }
//
//        override fun areContentsTheSame(
//            oldItem: ColorItem,
//            newItem: ColorItem
//        ): Boolean {
//            return oldItem == newItem
//        }
//    }
//
//    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
//        getItem(position)?.let { model ->
//            holder.bind(model, holder.itemView.context)
//        }
//    }
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ColorViewHolder {
//        val binding: ListItemColorBinding = DataBindingUtil.inflate(
//            LayoutInflater.from(viewGroup.context),
//            R.layout.list_item_color,
//            viewGroup,
//            false
//        )
//        return ColorViewHolder(binding)
//    }
//}
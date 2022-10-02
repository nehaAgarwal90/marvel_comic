package com.example.color_palette.presentation.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.color_palette.R
import com.example.color_palette.presentation.activity.ColorInfoActivity
import kotlinx.android.synthetic.main.fragment_palette.view.*
import kotlinx.android.synthetic.main.list_item_color.view.*

class PalettePickerFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_palette, container, false)
        init(view)
        return view
    }

    private val listColors = mutableListOf(ColorItem(Color.DKGRAY),
        ColorItem(Color.BLACK), ColorItem(Color.YELLOW))

    private val adapter  = ColorAdapter(listColors)


    fun init(view: View){
        Glide.with(this).asBitmap().load(R.drawable.sample_image)

            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                    return true
                }

                override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    resource?.let {
                        view.colorDropperView.setBackingBitmap(resource)
                    }
                    return true
                }

            }).submit(view.imageViewPalette.measuredWidth, view.imageViewPalette.measuredHeight)

        view.recyclerView?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        view.recyclerView?.adapter = adapter

        view.buttonAddColor.setOnClickListener {
           // val colors = listColors.toMutableList()
            listColors.add(0, ColorItem(view.colorDropperView.currentColor))
            adapter.setItem(listColors)
        }

    }

    class ColorAdapter(var items: List<ColorItem>) : RecyclerView.Adapter<ColorViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ColorViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_color, parent, false))

        override fun getItemCount() = items.size

        override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
            val item = items[position]
            holder.bind(item)
        }

        fun setItem(items: List<ColorItem>){
            this.items = items
            notifyDataSetChanged()
        }

    }

    class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(colorItem: ColorItem) {
            itemView.colorItemView.setBackgroundColor(colorItem.colorInt)
            itemView.setOnClickListener {
                startColorDetailsActivity(colorItem)
            }
        }

        fun startColorDetailsActivity(colorItem: ColorItem){
            val intent = Intent(itemView.context, ColorInfoActivity::class.java)

            intent.putExtra("hexcode", colorItem.colorInt)
            itemView.context.startActivity(intent)
        }
    }

    data class ColorItem(val colorInt: Int)
}
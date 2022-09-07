package com.example.marvel_comic.presentation.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import com.example.marvel_comic.R


interface OnMarvelCharacterFilterChangedListener {
    fun onMarvelCharacterFilterChanged(userChoice: CharSequence)
}

class MarvelCharacterFilterView(context: Context, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    val editTextView: EditText by lazy {
        findViewById<EditText>(R.id.name_edit_text)
    }

    private var listener: OnMarvelCharacterFilterChangedListener? = null

    init {
        View.inflate(context, R.layout.filter_view, this)
        editTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                listener?.onMarvelCharacterFilterChanged(s)
            }
        })
    }

    fun setFilterChangedListener(listener: OnMarvelCharacterFilterChangedListener?) {
        this.listener = listener
    }
}
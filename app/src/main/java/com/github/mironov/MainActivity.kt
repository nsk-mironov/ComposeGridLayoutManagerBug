package com.github.mironov

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(createRecycler())
  }

  private fun createRecycler(): RecyclerView {
    val recycler = RecyclerView(this)
    val numColumns = 2
    recycler.layoutManager = GridLayoutManager(this, numColumns)
    recycler.adapter = ContentAdapter(numColumns)
    recycler.background = ColorDrawable(Color(0xFFF2F3F7).toArgb())
    recycler.layoutParams = ViewGroup.MarginLayoutParams(
      ViewGroup.LayoutParams.MATCH_PARENT,
      ViewGroup.LayoutParams.MATCH_PARENT,
    )
    return recycler
  }
}

private class ContentAdapter(
  private val numColumns: Int,
  private val numItems: Int = 100
) : RecyclerView.Adapter<ContentItemViewHolder>() {
  private val random = Random.Default
  private val items = ArrayList<String>()

  init {
    generateContent()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentItemViewHolder {
    val view = ComposeView(parent.context)
    view.layoutParams = ViewGroup.MarginLayoutParams(
      ViewGroup.LayoutParams.MATCH_PARENT,
      ViewGroup.LayoutParams.WRAP_CONTENT,
    )
    return ContentItemViewHolder(view)
  }

  override fun onBindViewHolder(holder: ContentItemViewHolder, position: Int) {
    val onStart = position % numColumns == 0
    val onEnd = position % numColumns == numColumns - 1

    val onTop = position < numColumns
    val onBottom = position > itemCount - numColumns

    val text = items[position]

    holder.view.setContent {
      Text(
        text = text,
        textAlign = TextAlign.Center,
        color = Color(0xFF2D2F43),
        modifier = Modifier
          .fillMaxSize()
          .padding(
            start = if (onStart) 0.dp else 4.dp,
            end = if (onEnd) 0.dp else 4.dp,
            top = if (onTop) 8.dp else 4.dp,
            bottom = if (onBottom) 8.dp else 4.dp,
          )
          .background(Color(0xFFFFFFFF))
          .clickable { generateContent() }
          .padding(24.dp)
          .wrapContentSize(Alignment.Center)
      )
    }
  }

  override fun getItemCount(): Int {
    return numItems
  }

  private fun generateContent() {
    items.clear()
    repeat(itemCount) { index ->
      items += List(random.nextInt(1, 10)) { "Item #$index" }.joinToString(". ")
    }
    notifyDataSetChanged()
  }
}

private class ContentItemViewHolder(
  val view: ComposeView
) : RecyclerView.ViewHolder(view)

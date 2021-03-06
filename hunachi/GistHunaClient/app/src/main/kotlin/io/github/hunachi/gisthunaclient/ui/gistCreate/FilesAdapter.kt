package io.github.hunachi.gisthunaclient.ui.gistCreate

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.hunachi.gisthunaclient.R
import io.github.hunachi.gisthunaclient.databinding.FragmentFileBinding
import io.github.hunachi.model.File
import io.github.hunachi.shared.inflate

class FilesAdapter(
        private val listener: FilesAdapter.FilesListener
) : ListAdapter<File, FilesAdapter.ViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.fragment_file, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            file = getItem(position)
            addFileButton.setOnClickListener {
                addFileButton.visibility = View.GONE
                listener.addFile()
            }
        }
    }

    fun allFiles(): List<File> = mutableListOf<File>().apply {
        (0 until itemCount).forEach { add(getItem(it)) }
    }

    fun allNotEmptyFile(): List<File> {
        val list = mutableListOf<File>()
        (0 until itemCount).forEach {
            val file = getItem(it)
            if (file.content.isNotBlank()) list.add(file)
        }
        return list
    }

    interface FilesListener {
        fun addFile()
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<File>() {
            override fun areItemsTheSame(oldItem: File, newItem: File) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: File, newItem: File) = oldItem == newItem
        }
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val binding: FragmentFileBinding = FragmentFileBinding.bind(view)
    }
}
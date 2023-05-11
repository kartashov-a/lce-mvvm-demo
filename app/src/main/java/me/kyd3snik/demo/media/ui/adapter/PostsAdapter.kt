package me.kyd3snik.demo.media.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.kyd3snik.demo.databinding.ViewPostItemBinding
import me.kyd3snik.demo.media.data.Post

class PostsAdapter(
    private val onPostClicked: (String) -> Unit
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffUtils) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ViewPostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    inner class ViewHolder(private val viewBinding: ViewPostItemBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        private lateinit var postId: String

        init {
            viewBinding.root.setOnClickListener { onPostClicked(postId) }
        }

        fun bind(post: Post) = with(viewBinding) {
            postId = post.id
            title.text = post.title
        }
    }


    private object DiffUtils : DiffUtil.ItemCallback<Post>() {

        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem

    }
}
package me.kyd3snik.demo.media.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.kyd3snik.demo.databinding.ViewTopicItemBinding
import me.kyd3snik.demo.media.ui.model.TopicViewState

class TopicsAdapter(
    private val onTopicClicked: (String) -> Unit
) : ListAdapter<TopicViewState, TopicsAdapter.ViewHolder>(DiffUtils) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ViewTopicItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    inner class ViewHolder(private val viewBinding: ViewTopicItemBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        private lateinit var topicId: String

        init {
            viewBinding.root.setOnClickListener { onTopicClicked(topicId) }
        }

        fun bind(topic: TopicViewState) = with(viewBinding) {
            topicId = topic.id
            root.isSelected = topic.isSelected
            title.text = topic.title
        }
    }


    private object DiffUtils : DiffUtil.ItemCallback<TopicViewState>() {

        override fun areItemsTheSame(oldItem: TopicViewState, newItem: TopicViewState): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TopicViewState, newItem: TopicViewState): Boolean = oldItem == newItem

    }
}
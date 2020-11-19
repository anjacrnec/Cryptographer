package com.appbundles.exercises_storage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_exercise.view.*


class StoredExercisesAdapter(
    var exercises: List<StoredExercise>
): RecyclerView.Adapter<StoredExercisesAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise, parent, false)
        return ViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=exercises.get(position)
        holder.title.text=item.title
        holder.answer.text=item.answer
        holder.body.text=item.body
    }

    override fun getItemCount(): Int {
        return  exercises.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.itemExerciseTitle
        val body = itemView.itemExerciseBody
        val answer=itemView.itemExerciseAnswer
    }



}
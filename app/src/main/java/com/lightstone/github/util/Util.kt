package com.lightstone.github.util

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lightstone.github.R

fun getProgressDrawable(context: Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 5f
        centerRadius = 25f
        start()
    }
}

fun ImageView.loadImage(uri : String?, progressDrawable: CircularProgressDrawable) {
    val options  = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}

@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, url : String?) {
    view.loadImage(url, getProgressDrawable(view.context))
}

@BindingAdapter("repository")
fun TextView.setRepository(name : String?) {
    this.text = if(name.isNullOrEmpty()) "" else "$name's Repositories"
}

@BindingAdapter("created_on")
fun TextView.setDateCreated(date : String?) {
    this.text = if(date.isNullOrEmpty()) "" else "Created on : $date"
}

@BindingAdapter("updated_on")
fun TextView.setDateUpdated(date : String?) {
    this.text = if(date.isNullOrEmpty()) "" else "Updated on : $date"
}

@BindingAdapter("watchers")
fun TextView.setWatchers(watchers : String?) {
    this.text = if(watchers.isNullOrEmpty()) "" else "Watchers : $watchers"
}

@BindingAdapter("language")
fun TextView.setLanguage(language : String?) {
    this.text = if(language.isNullOrEmpty()) "" else "Language : $language"
}

@BindingAdapter("forks")
fun TextView.setForks(forks : String?) {
    this.text = if(forks.isNullOrEmpty()) "" else "Forks : $forks"
}

@BindingAdapter("open_issues")
fun TextView.setOpenIssues(open_issues : String?) {
    this.text = if(open_issues.isNullOrEmpty()) "" else "Open issues : $open_issues"
}

@BindingAdapter("size")
fun TextView.setSize(size : String?) {
    this.text = if(size.isNullOrEmpty()) "" else "Size : $size"
}

@BindingAdapter("subscribers_count")
fun TextView.setSubsCount(subscribers_count : String?) {
    this.text = if(subscribers_count.isNullOrEmpty()) "" else "Subscribers Count : $subscribers_count"
}
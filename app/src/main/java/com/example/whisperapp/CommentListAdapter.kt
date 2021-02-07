package com.example.whisperapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.RealmBaseAdapter
import io.realm.RealmResults

class CommentListAdapter(realmResult: RealmResults<Commu_commentDB>)
    :RealmBaseAdapter<Commu_commentDB>(realmResult) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val vh: ViewHolder_comment
        val view: View

        if(convertView==null){
            view=LayoutInflater.from(parent?.context).inflate(R.layout.item_content_commu,parent, false)
            vh=ViewHolder_comment(view)
            view.tag=vh
        }else{
            view=convertView
            vh=view.tag as ViewHolder_comment
        }
        if(adapterData!=null){
            val item=adapterData!![position]
            vh.commentTextView.text=item.comment
            vh.dateCommentTextView.text= android.text.format.DateFormat.format("yyyy/MM/dd", item.date)
        }
        return view
    }

    override fun getItemId(position: Int): Long {
        if(adapterData!=null){
            return adapterData!![position].id
        }
        return super.getItemId(position)
    }
}

class ViewHolder_comment(view: View){
    val commentTextView: TextView=view.findViewById(R.id.comment_commu)
    val dateCommentTextView: TextView=view.findViewById(R.id.commenttime_commu)
}
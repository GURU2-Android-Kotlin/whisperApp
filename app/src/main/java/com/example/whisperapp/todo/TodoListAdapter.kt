package com.example.whisperapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.whisperapp.todo.Todo
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter

class TodoListAdapter(realmResult: OrderedRealmCollection<Todo>)
    : RealmBaseAdapter<Todo>(realmResult){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val vh: ViewHolder
        val view: View

        if(convertView==null){
            view= LayoutInflater.from(parent?.context).inflate(R.layout.todo_item,parent, false)
            vh=ViewHolder(view)
            view.tag=vh
        }else{
            view=convertView
            vh=view.tag as ViewHolder
        }
        if(adapterData!=null){
            val item=adapterData!![position]
            vh.textTextView.text=item.title
            vh.subTextView.text=item.subtitle
            vh.dateTextView.text = android.text.format.DateFormat.format("yyyy/MM/dd", item.date)
        }
        return view
    }

    override fun getItemId(position: Int): Long {
        if (adapterData!= null) {
            return adapterData!![position].id
            // adapterView가 Realm 데이터를 가지고 있으므로
            // 해당 위치의 id를 반환해줘야 함
        }
        return super.getItemId(position)
    }
}
class ViewHolder(view: View) {
    val dateTextView: TextView = view.findViewById(R.id.text1)
    val textTextView: TextView = view.findViewById(R.id.text2)
    val subTextView: TextView = view.findViewById(R.id.text3)
}
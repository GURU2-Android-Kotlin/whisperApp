package com.example.whisperapp.event

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import com.example.whisperapp.R

class EventListActivity : AppCompatActivity() {

    lateinit var listView:ListView
    lateinit var allBtn:Button
    lateinit var contestBtn:Button
    lateinit var eduBtn:Button
    lateinit var volunteerBtn:Button
    lateinit var studyBtn:Button
    lateinit var otherBtn:Button

    var contentList= arrayListOf<ContentClass>(
        ContentClass("K-shield N기 모집",
            "대상: 김성연\n" +
                    "주최: 김성연 집\n" +
                    "모집 기간: 2020/02/02~2020/02/02",
            "봉사활동",
            "K-shield N기 모집K-shield N기 모집K-shield N기 모집",
            "k_shield",
            "http://www.naver.com"),
        ContentClass("Best Of the Best 모집",
            "대상: 김성연\n" +
                    "주최: 김성연 집\n" +
                    "모집 기간: 2020/02/02~2020/02/02",
            "교육",
            "Best Of the Best 모집Best Of the Best 모집Best Of the Best 모집",
            "bob",
            ""),
        ContentClass("정보보호 소학회 SWLUG",
            "대상: 김성연\n" +
                    "주최: 김성연 집\n" +
                    "모집 기간: 2020/02/02~2020/02/02",
            "스터디/동아리",
            "정보보호 소학회 SWLUG정보보호 소학회 SWLUG정보보호 소학회 SWLUG",
            "swlug",
            ""),
        ContentClass("인하대 코딩경진대회",
            "대상: 김성연\n" +
                    "주최: 김성연 집\n" +
                    "모집 기간: 2020/02/02~2020/02/02",
            "대회/공모전",
            "인하대 코딩경진대회인하대 코딩경진대회인하대 코딩경진대회",
            "k_shield",
            ""),
        ContentClass("김성연 팬카페",
            "대상: 김성연\n" +
                    "주최: 김성연 집\n" +
                    "모집 기간: 2020/02/02~2020/02/02",
            "스터디/동아리",
            "김성연 팬카페김성연 팬카페김성연 팬카페김성연 팬카페",
            "",
            "")
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_listview)

        listView = findViewById(R.id.listview)
        contestBtn=findViewById(R.id.contestBtn)
        volunteerBtn=findViewById(R.id.volunteerBtn)
        studyBtn=findViewById(R.id.studyBtn)
        allBtn=findViewById(R.id.allBtn)
        otherBtn=findViewById(R.id.otherBtn)
        eduBtn=findViewById(R.id.eduBtn)
        //val listView_Adapter=ArrayAdapter(this, android.R.layout.simple_list_item_1, contestList)

        //AddPostActivity에서 넘어온 intent데이터를 contentList 배열에 추가
        if(intent.hasExtra("title")){
            contentList.add(
                ContentClass(intent.getStringExtra("title").toString(),
                    intent.getStringExtra("content").toString(),
                    intent.getStringExtra("sort").toString(),
                    intent.getStringExtra("detail").toString(),
                    intent.getStringExtra("mainImageView").toString(),
                    intent.getStringExtra("link").toString()
                )
            )
        }


        var tmp=contentList
        listView.adapter= ListViewAdapter(this, tmp)
        ListViewAdapter(this, tmp).notifyDataSetChanged()

        //Item click listener
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            //val selectItem = parent.getItemAtPosition(position) as String
            //selectName.text = selectItem
            //Toast.makeText(this, MyCustomAdapter(this).getItem(position).toString(), Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "자세한 내용 확인", Toast.LENGTH_SHORT).show()
            val intent= Intent(this, EventItemActivity::class.java)
            intent.putExtra("title", ListViewAdapter(this, tmp).getTitleView(position))
            intent.putExtra("content", ListViewAdapter(this, tmp).getContentView(position))
            intent.putExtra("detail", ListViewAdapter(this, tmp).getDetailView(position))
            intent.putExtra("mainImageView", ListViewAdapter(this, tmp).getImageView(position))
            intent.putExtra("link", ListViewAdapter(this, tmp).getLink(position))
            startActivity(intent)
        }

        allBtn.setOnClickListener {
            listView.adapter = ListViewAdapter(this, tmp)

            for (i in 0..contentList.size - 1) {
                tmp[i].bool = true
            }
        }
        contestBtn.setOnClickListener {
            listView.adapter = ListViewAdapter(this, tmp)

            for (i in 0..contentList.size - 1) {
                tmp[i].bool = tmp[i].sort.equals("대회/공모전")
            }
        }
        volunteerBtn.setOnClickListener {
            listView.adapter= ListViewAdapter(this, tmp)

            for(i in 0..contentList.size-1){
                tmp[i].bool = tmp[i].sort.equals("봉사활동")
            }
        }
        studyBtn.setOnClickListener {
            listView.adapter= ListViewAdapter(this, tmp)

            for(i in 0..contentList.size-1){
                tmp[i].bool = tmp[i].sort.equals("스터디/동아리")
            }
        }
        eduBtn.setOnClickListener {
            listView.adapter= ListViewAdapter(this, tmp)

            for(i in 0..contentList.size-1){
                tmp[i].bool = tmp[i].sort.equals("교육")
            }
        }
        otherBtn.setOnClickListener {
            listView.adapter= ListViewAdapter(this, tmp)

            for(i in 0..contentList.size-1){
                tmp[i].bool = tmp[i].sort.equals("기타")
            }
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.addpost_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(this, "스터디/동아리 추가하기", Toast.LENGTH_SHORT).show()
        val intent= Intent(this, AddPostActivity::class.java)
        startActivity(intent)
        return true
    }

}

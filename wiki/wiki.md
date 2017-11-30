# AniDesu

## Features

- สามารถเข้าสู่ระบบโดยใช้ Facebook Account ได้
- สามารถรีวิว (Review) อนิเมะที่ดูแล้วได้
- สามารถเพิ่ม (Add) หรือแก้ไข (Edit) รายการอนิเมะที่ จะดู (Plan to watch), กำลังดู (Watching), ดูจบแล้ว (Completed) และเลิกดู (Dropped) แล้วได้
- สามารถทำการ Post Status, comment และ กด like ได้
- สามารถค้นหาอนิเมะที่สนใจได้



## Interface

### Theme

<Loading…….>



### Layout

- **Welcome :** หน้าแรกที่เข้ามาจะเป็นหน้า Login ต้องใช้ Facebook Account แต่ถ้า Login ไว้แล้วจะเด้งไปหน้า Home
- **Navigation Bar :** แสดง Menu และข้อมูลส่วนตัวของ User
- **Home :** หน้าหลักของ Application จะเป็นหน้าที่รวบรวม Status (Feed) ของสมาชิก AniDesu ทั้งหมดที่ทำการ Post ไว้ โดยในหน้า Home นั้นจะประกอบไปด้วยหน้าย่อย ๆ ได้อีก 2 หน้า คือ
  - Create Post : หน้าที่ไว้ให้สำหรับสมาชิกต้องการจะ Post Status
  - Comment this post : หน้าที่ไว้เมื่อสมาชิกต้องการจะแสดงความคิดเห็น (Comment) ของ Post ที่สนใจ
- **Discover Anime :** หน้าที่รวบรวมและแสดงอนิเมะญี่ปุ่นทั้งหมด โดยจะแบ่งเป็น ฤดูกาล (Season) ได้แก่ Winter, Spring, Summer และ Fall โดยเมื่อเลือกดูข้อมูลของอนิเมะแต่ละเรื่อง
- **Anime Overview :** หน้าที่แสดงรายละเอียดของอนิเมะแต่ละเรื่อง ตามที่ User เลือกดู โดยจะแบ่งเป็นหน้าย่อย ๆ ได้อีก 4 หน้า คือ
  - Info : หน้าที่แสดงรายละเอียดของอนิเมะ เช่น ชื่อเรื่อง, เรื่องย่อ, ประเภท, จำนวนตอน, ฉายวันที่ ฯลฯ เป็นต้น
  - Stats : หน้าที่แสดง Website ที่เกี่ยวข้องกับ Anime เรื่องนั้น ๆ และแสดงจำนวนที่สมาชิก ทำการ Add to My Anime List ไว้
  - Extras : หน้าที่แสดงชื่อตัวละคร และผู้สร้างที่อยู่ใน Anime เรื่องนั้น ๆ
  - Reviews : หน้าที่แสดง Reviews ของสมาชิกที่ได้ดูเรื่องนี้ ไม่ว่าจะเป็น Reviews เชิงบวกหรือเชิงลบ
- **ADD/EDIT My Anime List :** หน้าที่ เพิ่ม แก้ไข ลบ Anime ที่อยู่ใน My Anime List โดยระบบจะเก็บ สถานะของ Anime, ตอนที่ดู, คะแนนโดยรวม และ Notes เป็นต้น
- **ADD Review :** หน้าที่ เพิ่ม Review ให้กับ Anime ที่สนใจ โดยการคะแนน (ดาว) และใส่ข้อความที่ต้องการจะ Review
- **Share Anime :** หน้าที่ทำการ Share Anime ไปยัง Application อื่น ๆ
- **My Anime List :** หน้าที่ทำการแสดง Anime ทั้งหมดที่ User ทำการ Add ไว้ โดยจะแบ่งเป็น 4 ประเภทคือ Plan to Watch (วางแผนจะดู), Watching (กำลังดู), Completed (ดูเสร็จแล้ว) และ เลิกดู (Dropped)  โดยจะแสดงข้อมูลแบบคร่าว ๆ เช่น ชื่อเรื่อง, ตอนที่กำลังดู และคะแนน เป็นต้น
- **Anime Review :** หน้าที่รวมข้อมูลการ Review ของทุก User ที่ทำการ Review ไว้ เมื่อกดที่ Read more จะทำการแสดง Review ของ Review นั้น ๆ โดยละเอียด
- **Anime Search :** ช่องที่ไว้สำหรับการค้นหา หา Anime ที่สนใจ และแสดง Anime ทั้งหมด ที่ใกล้เคียงกับคำค้นหา

## APIs

### Anilist API

```html
https://anilist.co/api/
```

ใช้เพื่อดึงข้อมูลอนิเมะทั้งหมด ไม่ว่าจะเป็น ชื่อเรื่อง, วันฉายอนิเมะ, วันที่อนิเมะจบ, จำนวนตอนทั้งหมด, รูปของอนิเมะนั้น ๆ ฯลฯ เป็นต้น



#### Access Token

<Loading…….>

##### Request example :

```
POST: auth/access_token

Url Parms:
grant_type    : "client_credentials"
client_id     : "bbellkungdesu-kungd"
client_secret : "L5gVawdnzKUYaABC2saoXZRq0rz"
```

##### Response example :

```json
{
    access_token: "NR3M3vXgHK0kmluOcJVlRXvbGOg4yLhAVyf5If"
    token_type: "bearer"
    expires: 1414234981
    expires_in: 3600
}
```



#### Browse Anime

<Loading…….>

##### Request Example : 

```
Get: browse/{series_type}

Header:
Authorization	: "Bearer NR3M3vXgHK0kmluOcJVlRXvbGOg4yLhAVyf5If"

Path:
{series_type}	: "anime"

Url Parms:
year			: 2017
season			: "winter"
full_page		: true
airing_Data		: true
```

##### Response example :

```json
[{
    "id": 17947,
    "title_romaji": "Mahou Shoujo Lyrical Nanoha: Reflection",
    "title_english": "Magical Girl Lyrical Nanoha: Reflection",
    "title_japanese": "魔法少女リリカルなのはReflection",
    "type": "Movie",
    "start_date_fuzzy": 20170722,
    "end_date_fuzzy": 20170722,
    "season": 173,
    "series_type": "anime",
    "synonyms": [],
    "genres": [
        "Action",
        "Comedy",
        "Drama",
        "Mahou Shoujo"
    ],
    "adult": false,
    "average_score": 0,
    "popularity": 539,
    "updated_at": 1511801686,
    "hashtag": null,
    "image_url_sml": "https://cdn.anilist.co/img/dir/anime/sml/17947-QTSoKwtDlzFJ.jpg",
    "image_url_med": "https://cdn.anilist.co/img/dir/anime/med/17947-QTSoKwtDlzFJ.jpg",
    "image_url_lge": "https://cdn.anilist.co/img/dir/anime/reg/17947-QTSoKwtDlzFJ.jpg",
    "image_url_banner": null,
    "total_episodes": 1,
    "airing_status": "finished airing",
    "airing": null,
    "tags": [
        {
            "id": 29,
            "name": "Magic",
            "description": "Prominently features magical elements or the use of magic.",
            "spoiler": false,
            "adult": false,
            "demographic": false,
            "denied": 0,
            "category": "Theme-Fantasy",
            "votes": 79,
            "series_spoiler": false
        }
    ]
}, 
/// More........................
]
```


#### Fetch Anime Data

<Loading…….>

##### Request example : 

```
GET: {series}/{id}/page

Header:
Authorization	: "Bearer NR3M3vXgHK0kmluOcJVlRXvbGOg4yLhAVyf5If"

Path:
{series}		: "anime"
{id}			: 21825
```

##### Response example :

```json
{
    "id": 21825,
    "title_romaji": "Danganronpa 3: The End of Kibougamine Gakuen - Zetsubou-hen",
    "title_english": "Danganronpa 3: The End of Hope’s Peak High School - Despair Arc",
    "title_japanese": "ダンガンロンパ3 -The End of 希望ヶ峰学園-　絶望編",
    "type": "TV",
    "series_type": "anime",
    "start_date": "2016-07-14T00:00:00+09:00",
    "end_date": "2016-09-22T00:00:00+09:00",
    "start_date_fuzzy": 20160714,
    "end_date_fuzzy": 20160922,
    "season": 163,
    "description": "The prequel to the 2012 PSP game, Danganronpa 2: Goodbye Despair. It will tell the story of the Remnants of Despair, a group featured in the game, while the other anime airing alongside (Mirai-hen) will act as a sequel to the game.",
    "adult": false,
    "average_score": 73,
    "mean_score": 75,
    "popularity": 2443,
    "favourite": false,
    "image_url_sml": "https://cdn.anilist.co/img/dir/anime/sml/21825-oV4EMLnoWVYh.png",
    "image_url_med": "https://cdn.anilist.co/img/dir/anime/med/21825-oV4EMLnoWVYh.png",
    "image_url_lge": "https://cdn.anilist.co/img/dir/anime/reg/21825-oV4EMLnoWVYh.png",
    "image_url_banner": null,
    "genres": [
        "Action",
        "Horror",
        "Mystery",
        "Psychological"
    ],
    "synonyms": [],
    "youtube_id": null,
    "hashtag": "#ダンガンロンパ",
    "updated_at": 1511801354,
    "total_episodes": 11,
    "duration": 24,
    "airing_status": "finished airing",
  /// More.................
}
```



#### Anime Search

<Loading…….>

##### Request example : 

```
GET: {series_type}/search/{query}

Header:
Authorization	: "Bearer NR3M3vXgHK0kmluOcJVlRXvbGOg4yLhAVyf5If"

Path:
{series_type}	: "anime"
{query}			: "umaru"
```

##### Response example :

```json
[
{
    "id": 20987,
    "title_romaji": "Himouto! Umaru-chan",
    "title_english": "Himouto! Umaru-chan",
    "title_japanese": "干物妹！うまるちゃん",
    "type": "TV",
    "series_type": "anime",
    "start_date": "2015-07-09T00:00:00+09:00",
    "end_date": "2015-09-24T00:00:00+09:00",
    "start_date_fuzzy": 20150709,
    "end_date_fuzzy": 20150924,
    "season": 153,
    "description": "An anime adaptation of Himouto! Umaru-chan manga. The sibling gag comedy manga centers around Umaru, Taihei's little sister who boasts beautiful looks as well as prowess in both school and sports. However, \"Himouto\" (beautiful little sister) has a certain secret.<br>\n<br>\n(Source ANN)",
    "adult": false,
    "average_score": 71,
    "mean_score": 72,
    "popularity": 5994,
    "favourite": false,
    "image_url_sml": "https://cdn.anilist.co/img/dir/anime/sml/20987-tHsGmyqcZFV2.png",
    "image_url_med": "https://cdn.anilist.co/img/dir/anime/med/20987-tHsGmyqcZFV2.png",
    "image_url_lge": "https://cdn.anilist.co/img/dir/anime/reg/20987-tHsGmyqcZFV2.png",
    "image_url_banner": "https://cdn.anilist.co/img/dir/anime/banner/20987-3bjbyj06ZcSF.jpg",
    "genres": [
        "Comedy",
        "Slice of Life"
    ],
    "synonyms": [],
    "youtube_id": "J92h4Fr3WiM",
    "hashtag": "#umaru_anime",
    "updated_at": 1511887840,
    "total_episodes": 12,
    "duration": 24,
    "airing_status": "finished airing",
    "source": "Manga",
    "classification": "",
    "airing_stats": []
},
{
    "id": 21327,
    "title_romaji": "Himouto! Umaru-chan OVA",
    "title_english": "Himouto! Umaru-chan OVA",
    "title_japanese": "干物妹！うまるちゃん OVA",
    "type": "OVA",
    "series_type": "anime",
    "start_date": "2015-10-19T00:00:00+09:00",
    "end_date": "2017-04-19T00:00:00+09:00",
    "start_date_fuzzy": 20151019,
    "end_date_fuzzy": 20170419,
    "season": 154,
    "description": "Episodes included in the manga's seventh and tenth volume.",
    "adult": false,
    "average_score": 69,
    "mean_score": 72,
    "popularity": 1030,
    "favourite": false,
    "image_url_sml": "https://cdn.anilist.co/img/dir/anime/sml/21327-1qTi4wb3XyxB.jpg",
    "image_url_med": "https://cdn.anilist.co/img/dir/anime/med/21327-1qTi4wb3XyxB.jpg",
    "image_url_lge": "https://cdn.anilist.co/img/dir/anime/reg/21327-1qTi4wb3XyxB.jpg",
    "image_url_banner": null,
    "genres": [
        "Comedy",
        "Slice of Life"
    ],
    "synonyms": [],
    "youtube_id": null,
    "hashtag": "#umaru_anime",
    "updated_at": 1511801427,
    "total_episodes": 2,
    "duration": 24,
    "airing_status": "finished airing",
    "source": "Manga",
    "classification": "",
    "airing_stats": []
},
  /// More...............
]
```


### Firebase API

<Loading…….>



## Review

### My Review

<Loading…….>

### Other Review

<Loading…….>



## Testing

### UI Testing

<Loading…….>

### UNIT Testing

<Loading…….>



## APK

<Loading…….>
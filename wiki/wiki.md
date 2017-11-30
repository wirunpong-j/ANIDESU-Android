# AniDesu

## Features

- สามารถเข้าสู่ระบบโดยใช้ Facebook Account ได้
- สามารถรีวิว (Review) อนิเมะที่ดูแล้วได้
- สามารถเพิ่ม (Add) หรือแก้ไข (Edit) รายการอนิเมะที่ จะดู (Plan to watch), กำลังดู (Watching), ดูจบแล้ว (Completed) และเลิกดู (Dropped) แล้วได้
- สามารถทำการ Post Status, comment และ กด like ได้
- สามารถค้นหาอนิเมะที่สนใจได้



## Design

### Theme

<Loading…….>



### Layout

<Loading…….>



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
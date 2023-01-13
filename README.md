# Museek Music Streaming Application

### Museek Music Streaming Application 
is a final project from SC312303 Mobile Application Development

## โครงสร้างของแอปพลิเคชัน


*• หน้าสำหรับรอโหลด เป็นหน้าที่จะเช็คว่ามี User ค้างอยู่หรือไม่ถ้าเกิดว่ามี User อยู่จะพาไปยังหน้า Dashboard
ทันที แต่ถ้าหากเช็คแล้วไม่พบ User ค้างไว้ ก็จะพาผู้ใช้งานไปยังหน้าเลือก Google 
Account*<br>

![Semantic description of image](https://user-images.githubusercontent.com/81796447/212316947-4e02b5c7-7285-46ad-bf94-c6ff68455c20.png) ![image](https://user-images.githubusercontent.com/81796447/212318331-74cc6b21-bdda-41cd-be13-25616cb0eafe.png)    ![image](https://user-images.githubusercontent.com/81796447/212318350-66517e7f-be00-4fbd-afe4-b206e4714f1c.png)





*• หน้า Dashboard หน้าที่จะดึงข้อมูลจาก Realtime Database เพื่อนำมาแสดงผล โดยที่เพลงที่ถูกแนะนำจะถูก
นับมาจากยอดวิว*
![image](https://user-images.githubusercontent.com/81796447/212318680-c9eeb7b8-64ea-4214-bc3e-f80f9cb580fa.png)

### รูปแบบของฐานข้อมูลจาก Firebase 

![image](https://user-images.githubusercontent.com/81796447/212319013-880071dd-e9aa-44f2-8568-0f6f000b3261.png)

*ในส่วนของการค้นหา เราจะใช้การ Datasnapshot มาจากฐานข้อมูล* <br>
![image](https://user-images.githubusercontent.com/81796447/212319187-f011c8cf-60ac-419d-a5b0-6ed59ce5ca6e.png)
![image](https://user-images.githubusercontent.com/81796447/212319262-727c1a1a-cfe1-4df4-ba69-7a73b449264c.png)

* ในส่วนของระบบอัพโหลดนั้น * <br>
![image](https://user-images.githubusercontent.com/81796447/212319403-a04cc44b-71c3-4090-97a1-f6007c4759d8.png)
![image](https://user-images.githubusercontent.com/81796447/212319472-90c93ca6-e979-41c4-9a0e-17ea8961538a.png)


การอัพโหลดเพลงโดยจะให้ผู้ใช้งานเลือกรูปได้ ( ขนาดถูกฟิกซ์ให้ด้านเท่ากัน
เสมอ โดยใช้ scale:fitXY ) เลือกไฟล์เพลงได้ และตั้งชื่อเพลง เมื่อกดปุ่มอัพโหลดข้อมูลจะถูกเขียนลง 
Storage ดังนี้ <br>
![image](https://user-images.githubusercontent.com/81796447/212319571-8710e3c8-a4a5-4935-ba61-4cf6712c2a52.png)

## ระบบ Mediaplayer
![image](https://user-images.githubusercontent.com/81796447/212319857-13a37b53-b7aa-48e7-a7d5-7d7f7d492f33.png)

*ในส่วนนี้เป็นการใช้ Communicator โดยผมจะนำคลาสนี้ไว้ในหน้า Menu เนื่องหน้าเป็นหน้าที่ถูก
แสดงผลตลอดแต่ถูก Replace ด้วย fragment อื่นๆ โดยจะส่งชื่อเพลงไปครับ จากนั้น*<br>
![image](https://user-images.githubusercontent.com/81796447/212319962-6f0aa076-1b0f-440e-83a9-bc3a16431092.png)

![image](https://user-images.githubusercontent.com/81796447/212320076-990ce87f-e6f4-4a7a-bb59-fa5a0e9a2d33.png)

*นำชื่อที่ได้จากการส่งมาจากหน้า Dashboard หรือ Search มาเช็คกับ Arraylist ที่เรา 
Datasnapshot มาแล้วจาก Firebase เพื่อหาว่าชื่อเพลงที่ได้รับมาตรงกับชื่อใดใน Arraylist จากนั้น
เมื่อเจอแล้วก็จะนำ URL ของเพลงนั้นไปเปิดผ่าน Mediaplayer โดยที่ต้องส่งชื่อเพลงมาแทน
ตำแหน่งใน Arraylist นั้นเพราะว่าป้องกันการผิดพลาดของ Index เนื่องจากหน้า Dashboard มีการ
แสดงผลของสอง Recyclerview ที่ใช้ Arraylist คนละตัวกันแต่หน้าเล่นเพลงนั้นจะต้องรันเพลง
ต่อไปเรื่อยๆได*

________________________________________________________________________________________________________________

Reference ที่สำคัญต่อการพัฒนา <br>
Upload Image to Firebase Storage using Kotlin || Choose Image and 
Upload to Firebase || Kotlin : shorturl.at/BEHIS <br>
How to Add SearchView in Android App using Kotlin | SearchView | Kotlin | 
Android Studio Tutorial : shorturl.at/hjmzQ <br>
Fragment to Fragment Communication in Android Studio [Kotlin 2020] :
shorturl.at/EKQW1 <br>
Lets Play First Song in our App | Music Player Application | Android 
Development Kotlin in Hindi :
shorturl.at/eoFX3




# Suara Tangan: Interactive Bahasa Isyarat Malaysia (BIM) Mobile Learning Platform

## 📌 Project Overview
**Suara Tangan** is an Android-based mobile learning application developed as a Final Year Project (FYP) for the Bachelor of Software Engineering (Information System Development) program at **Universiti Kebangsaan Malaysia (UKM)**. 

While most existing sign language tools focus heavily on American Sign Language (ASL), **Suara Tangan** is specifically engineered to preserve and teach **Bahasa Isyarat Malaysia (BIM)**. The application serves as an interactive bridge between the hearing community and the deaf community in Malaysia by combining modern computer vision with accessible educational materials.

---

## 🛠️ Tech Stack & Tools
* **Mobile Development:** Android Studio (Kotlin / XML Architecture)
* **Computer Vision / AI:** Google MediaPipe (Real-time Hand Tracking & Gesture Recognition)
* **Backend & Cloud Database:** Firebase Firestore / Authentication
* **UI/UX Design:** Figma
* **Target OS:** Android 10.0 (API Level 29) and above

---

## 🧠 Key Features Implemented

### 1. Localized BIM Glossary & Video Dictionary
* Offers a comprehensive repository of BIM signs categorized into practical daily themes (e.g., numbers, alphabets, common phrases).
* Incorporates built-in **video playback speed controls**, enabling users to slow down intricate hand movements to learn precise gestures effectively.

### 2. Intelligent Real-Time Sign Recognition
* Leverages integrated camera feeds and **Google MediaPipe** tracking frameworks to capture live skeletal hand landmarks.
* Translates physical signs into text dynamically, providing instant validation for learners practicing their gestures.

### 3. Gamified Quiz & Assessment Module
* Features an interactive assessment system designed to test structural sign memory.
* Tracks user learning progression and score metrics locally and dynamically across modules.

### 4. Sentence-to-Sign Translation Interface
* Bridges communication gaps via a specialized module that translates structural user input queries directly into equivalent BIM video sequences.

---

## 📊 System Architecture & Requirements

The system layout segregates modern client-side execution from server-side verification:
1. **Presentation Layer (UI/UX):** Native Android XML layouts prioritizing high-contrast, clean elements tailored for optimized user accessibility.
2. **Logic Layer (MediaPipe/Kotlin):** Manages local resource calculation, extracts 21 individual 3D hand-joint coordinates, and maps them instantly to matching sign nodes.
3. **Data Layer (Firebase):** Manages secure authenticated profiles and hosts structured application assets.

---

## This is the interface

### Log Masuk & Pendaftaran Akaun
<img width="591" height="1280" alt="photo_3_2026-07-02_00-50-07" src="https://github.com/user-attachments/assets/4625440b-d6dd-4330-a488-ffd536563d83" />

<img width="591" height="1280" alt="photo_2_2026-07-02_00-50-07" src="https://github.com/user-attachments/assets/8bd4fe23-c761-4311-ae0a-2091dfc6ab99" />

### Halaman Utama
<img width="456" height="1280" alt="photo_10_2026-07-02_00-50-07" src="https://github.com/user-attachments/assets/199103bf-4e84-42d5-a26a-48d33c942b75" />

### Glosari
<img width="591" height="1280" alt="photo_1_2026-07-02_00-50-07" src="https://github.com/user-attachments/assets/c2ac8277-08c8-499d-9c99-961e06ff3172" />

### Kuiz
<img width="591" height="1280" alt="photo_9_2026-07-02_00-50-07" src="https://github.com/user-attachments/assets/6b183f57-b202-4395-980c-a2c9ed9286e5" />

<img width="591" height="1280" alt="photo_7_2026-07-02_00-50-07" src="https://github.com/user-attachments/assets/7374c30f-6a31-4852-9e4d-7dca01f1d968" />

<img width="591" height="1280" alt="photo_6_2026-07-02_00-50-07" src="https://github.com/user-attachments/assets/877ff81e-7059-4e31-81a4-b7e567c72d87" />

### Terjemahan
<img width="473" height="1280" alt="photo_5_2026-07-02_00-50-07" src="https://github.com/user-attachments/assets/2e26ca68-0de9-4508-8dbe-67b89415f6eb" />

### Penyemak Ejaan Jari
<img width="591" height="1280" alt="photo_4_2026-07-02_00-50-07" src="https://github.com/user-attachments/assets/7269047c-69d5-4fb8-a8ef-afa5ab7422a1" />

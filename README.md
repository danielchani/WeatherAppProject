# Cache Replacement Algorithms Project

פרויקט זה מממש מערכת מטמון (Cache) עם מספר אלגוריתמי החלפה. המערכת כוללת ממשק משתמש גרפי, ארכיטקטורת שרת-לקוח, ומימוש של שלושה אלגוריתמי החלפה נפוצים.

## אלגוריתמים שמומשו

1. **LRU (Least Recently Used)**
   - מחליף את הדף שלא היה בשימוש הכי הרבה זמן
   - משתמש ב-LinkedHashMap לסידור הדפים לפי סדר השימוש
   - יעיל במקרים של locality טמפורלי

2. **Random Replacement**
   - בוחר דף אקראי להחלפה כשהמטמון מלא
   - פשוט למימוש ויעיל בזיכרון
   - מתאים למקרים בהם אין תבנית גישה ברורה

3. **Second Chance (Clock)**
   - נותן "הזדמנות שנייה" לדפים לפני החלפתם
   - משתמש בביט שימוש לכל דף
   - פשוט יותר מ-LRU אך עדיין מנצל locality

## Implemented Algorithms

1. **LRU (Least Recently Used)**
   - Removes the least recently used page when cache is full
   - Uses LinkedHashMap for efficient implementation

2. **Random Replacement**
   - Randomly selects a page to remove when cache is full
   - Simple but can be effective in certain scenarios

3. **Second Chance (Clock)**
   - Gives pages a "second chance" before replacement
   - Uses a circular buffer with usage bits

## ארכיטקטורת המערכת

1. **שכבת האלגוריתמים**
   - `AlgoCache` - ממשק בסיסי למטמון
   - `LRUCache`, `RandomCache`, `SecondChanceCache` - מימושים שונים
   - `CacheContext` - מאפשר החלפת אלגוריתמים בזמן ריצה (Strategy Pattern)

2. **שכבת התקשורת**
   - `CacheServer` - שרת TCP שמקבל בקשות למטמון
   - `CacheClient` - לקוח CLI שמתחבר לשרת
   - פרוטוקול טקסטואלי פשוט לתקשורת

3. **ממשק משתמש**
   - `CacheGUI` - ממשק גרפי לניהול המטמון
   - תצוגה ויזואלית של תכולת המטמון
   - אפשרות להחלפת אלגוריתמים בזמן ריצה

## Design Patterns Used

- **Strategy Pattern**: Implemented through `CacheContext` class to allow dynamic switching between different cache algorithms
- **Interface-based Design**: All algorithms implement the `AlgoCache` interface

## How to Run

### הפעלת השרת
```powershell
cd "c:\path\to\project"
java main.Algos.Main server
```

### הפעלת לקוח
```powershell
java main.Algos.Main client
```

### הפעלת ממשק גרפי
```powershell
java main.Algos.CacheGUI
```

### הרצת הדגמה
```powershell
java main.Algos.Main
```

### הרצת בדיקות
```powershell
java -cp ".;junit-4.13.2.jar;hamcrest-core-1.3.jar" org.junit.runner.JUnitCore AlgoTests.AlgoCacheTest
```

## פקודות נתמכות (מצב לקוח)

- `request <page>` - בקשת דף מהמטמון
- `check <page>` - בדיקה אם דף נמצא במטמון
- `strategy <type> <size>` - החלפת אלגוריתם המטמון
  - `type`: lru, random, secondchance
  - `size`: גודל המטמון
- `quit` - יציאה

## דוגמאות שימוש

```java
// שימוש בסיסי
AlgoCache cache = new LRUCache(3);
cache.requestPage(1);
cache.requestPage(2);
boolean exists = cache.contains(1);

// החלפת אלגוריתמים בזמן ריצה
CacheContext context = new CacheContext(new LRUCache(3));
context.request(1);
context.setStrategy(new RandomCache(3));
context.request(2);
```

## Testing

The project includes JUnit tests in `AlgoCacheTest.java` that verify the behavior of each cache implementation.

## פיתוח והרחבות

1. להוסיף אלגוריתמי החלפה נוספים
2. לשפר את הממשק הגרפי עם אנימציות ותצוגת סטטיסטיקות
3. להוסיף תמיכה בסוגי נתונים שונים (לא רק מספרים)

## Project Structure

- `AlgoCache.java` - Main interface for cache algorithms
- `CacheContext.java` - Strategy pattern implementation
- `LRUCache.java` - LRU algorithm implementation
- `RandomCache.java` - Random replacement implementation
- `SecondChanceCache.java` - Second Chance algorithm implementation
- `Main.java` - Demo program
- `AlgoCacheTest.java` - Unit tests

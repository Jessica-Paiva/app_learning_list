
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.estaciogroup.learninglist.activities.Topic

class TopicDBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        const val DB_NAME = "mylist.db"
        const val DB_VERSION = 1
        private const val TABLE_NAME = "mylist"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "mytopic"
        private const val COLUMN_IS_COMPLETED = "is_completed"
        private const val COLUMN_TYPE = "type"
    }

    fun insertTask(task: Topic) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, task.name)
            put(COLUMN_IS_COMPLETED, if (task.isChecked) 1 else 0)
            put(COLUMN_TYPE, task.type.ifEmpty { "task" })
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_IS_COMPLETED INTEGER, $COLUMN_TYPE TEXT)"
        db?.execSQL(createTable)

         val topics = listOf(
            Topic(0, "Pesquisar sobre o que é Kotlin", false, "topic"),
            Topic(0, "Pesquisar o que são Variáveis", false, "topic"),
            Topic(0, "Revisar Tipos de dados", false, "topic"),
            Topic(0, "Como escrever um comentário em  Kotlin", false, "topic"),
            Topic(0, "Pesquisar sobre Estruturas de decisão", false, "topic"),
            Topic(0, "Pesquisar sobre Estruturas de dados", false, "topic"),
            Topic(0, "Pesquisar sobre Estruturas de repetição", false, "topic"),
            Topic(0, "Pesquisar sobre Funções", false, "topic"),
            Topic(0, "Pesquisar sobre Tratamento de Nulos", false, "topic"),
            Topic(0, "Pesquisar sobre Orientação a Objetos", false, "topic"),
            Topic(0, "Pesquisar sobre Android", false, "topic"),
            Topic(0, "Pesquisar sobre Ciclo de Vida Activity", false, "topic"),
            Topic(0, "Pesquisar sobre Gerenciadores de Layout", false, "topic"),
            Topic(0, "Pesquisar sobre Recyclerview", false, "topic"),
            Topic(0, "Pesquisar sobre Sqlite", false, "topic"),

        )

        topics.forEach { topic ->
            val values = ContentValues().apply {
                put(COLUMN_TITLE, topic.name)
                put(COLUMN_IS_COMPLETED, if (topic.isChecked) 1 else 0)
                put(COLUMN_TYPE, topic.type)
            }
            db?.insert(TABLE_NAME, null, values)
        }
    }

    fun getTopicById(id: Int): Topic {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $id"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val topic = Topic(
            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_COMPLETED)) == 1,
            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE))
        )
        cursor.close()
        db.close()
        return topic
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun updateTask(task: String, id: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, task)
        }
        db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }

    fun updateTaskCompletion(taskId: Int, isCompleted: Boolean) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_IS_COMPLETED, if (isCompleted) 1 else 0)
        }
        db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(taskId.toString()))
        db.close()
    }

    fun deleteTask(id: Int) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }

    fun getAllTaks(): List<Topic> {
        val taskList = mutableListOf<Topic>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_TYPE = 'task'"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_COMPLETED)) == 1
            val type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE))

            val task = Topic(id, title, isCompleted, type)
            taskList.add(task)

        }
        cursor.close()
        db.close()
        return taskList

    }


    fun loadTopics(): List<Topic> {
        val topics = mutableListOf<Topic>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_TYPE = 'topic'"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_COMPLETED)) == 1
            val type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE))

            val topic = Topic(id, title, isCompleted, type)
            topics.add(topic)
        }

        cursor.close()
        db.close()
        return topics
    }
}




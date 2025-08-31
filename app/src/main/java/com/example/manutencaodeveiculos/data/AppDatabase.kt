package com.example.manutencaodeveiculos.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// 1. VERSÃO INCREMENTADA DE 1 PARA 2
@Database(entities = [Usuario::class, Veiculo::class, Manutencao::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun veiculoDao(): VeiculoDao
    abstract fun manutencaoDao(): ManutencaoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // 2. DEFINIÇÃO DA MIGRAÇÃO (de versão 1 para 2)
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Comando SQL para adicionar a nova coluna 'categoria' à tabela 'manutencoes'.
                // 'NOT NULL DEFAULT 'Outros'' garante que registros antigos não quebrem o app.
                db.execSQL("ALTER TABLE manutencoes ADD COLUMN categoria TEXT NOT NULL DEFAULT 'Outros'")
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "manutencao_veiculos_db"
                )
                    // 3. MIGRAÇÃO ADICIONADA AO CONSTRUTOR
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
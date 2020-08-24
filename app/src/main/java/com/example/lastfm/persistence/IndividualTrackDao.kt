package com.example.lastfm.persistence

import androidx.room.*
import com.example.lastfm.domain.entities.track.IndividualTrackEntity

@Dao
interface IndividualTrackDao {
    @Query("select * from IndividualTrackEntity where individualTrack_id like :individualTrackId")
    suspend fun getLocalTrackByIDAsync(individualTrackId: String?): IndividualTrackEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(individualTrack: IndividualTrackEntity)
}

@Database(entities = [IndividualTrackEntity::class] , version = 2, exportSchema = false)
@TypeConverters(TrackConverter::class)
abstract class IndividualTrackDatabase: RoomDatabase() {
    abstract val individualTrackDao: IndividualTrackDao
}

package com.example.lastfm.persistence

import androidx.room.*
import com.example.lastfm.domain.entities.tracks.TrackSearchEntity

@Dao
interface TrackSearchDao {
    @Query("select * from TrackSearchEntity where trackSearch_id like :name")
    suspend fun getLocalTracksByNameAsync(name: String?): TrackSearchEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(trackSearchId: TrackSearchEntity)
}

@Database(entities = [TrackSearchEntity::class] , version = 1, exportSchema = false)
@TypeConverters(TrackConverter::class)
abstract class TrackSearchDefaultOrderDatabase: RoomDatabase() {
    abstract val trackSearchDao: TrackSearchDao
}

@Database(entities = [TrackSearchEntity::class] , version = 1, exportSchema = false)
@TypeConverters(TrackConverter::class)
abstract class TrackSearchTrackOrderDatabase: RoomDatabase() {
    abstract val trackSearchDao: TrackSearchDao
}

@Database(entities = [TrackSearchEntity::class] , version = 1, exportSchema = false)
@TypeConverters(TrackConverter::class)
abstract class TrackSearchArtistOrderDatabase: RoomDatabase() {
    abstract val trackSearchDao: TrackSearchDao
}

@Database(entities = [TrackSearchEntity::class] , version = 1, exportSchema = false)
@TypeConverters(TrackConverter::class)
abstract class TrackSearchListenersOrderDatabase: RoomDatabase() {
    abstract val trackSearchDao: TrackSearchDao
}

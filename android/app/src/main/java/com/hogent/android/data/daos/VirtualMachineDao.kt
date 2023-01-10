package com.hogent.android.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hogent.android.data.entities.VirtualMachine

@Dao
interface VirtualMachineDao {
    @Insert
    fun insert(vm: VirtualMachine)

    @Update
    fun update(vm: VirtualMachine)

    @Query("SELECT * FROM virtualmachine_table WHERE id = :key")
    fun get(key: Long): VirtualMachine?

    @Query("SELECT * FROM virtualmachine_table WHERE projectId = :key")
    fun getByProjectId(key: Long): List<VirtualMachine>?

}



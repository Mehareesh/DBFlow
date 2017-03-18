package com.raizlabs.android.dbflow.sql.language

import com.raizlabs.android.dbflow.BaseUnitTest
import com.raizlabs.android.dbflow.SimpleModel
import com.raizlabs.android.dbflow.SimpleModel_Table.name
import com.raizlabs.android.dbflow.TwoColumnModel
import com.raizlabs.android.dbflow.TwoColumnModel_Table
import com.raizlabs.android.dbflow.TwoColumnModel_Table.id
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.SQLite.select
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test

class FromTest : BaseUnitTest() {

    @Test
    fun validateSimpleFrom() {
        assertEquals("SELECT * FROM `SimpleModel`", (select from SimpleModel::class).query.trim())
    }

    @Test
    fun validateProjectionFrom() {
        assertEquals("SELECT `name` FROM `SimpleModel`", (select(name) from SimpleModel::class).query.trim())
    }

    @Test
    fun validateMultipleProjection() {
        assertEquals("SELECT `name`,`name`,`id` FROM `SimpleModel`",
            (select(name, TwoColumnModel_Table.name, id) from SimpleModel::class).query.trim())
    }

    @Test
    fun validateAlias() {
        assertEquals("SELECT * FROM `SimpleModel` AS `Simple`", (select from SimpleModel::class `as` "Simple").query.trim())
    }

    @Test
    fun validateJoins() {
        val from = (select from SimpleModel::class
            innerJoin TwoColumnModel::class
            on name.eq(TwoColumnModel_Table.name.withTable()))
        assertEquals("SELECT * FROM `SimpleModel` INNER JOIN `TwoColumnModel` ON `name`=`TwoColumnModel`.`name`",
            from.query.trim())
        assertTrue(from.associatedTables.isNotEmpty())
    }
}
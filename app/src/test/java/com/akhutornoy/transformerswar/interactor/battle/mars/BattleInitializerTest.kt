package com.akhutornoy.transformerswar.interactor.battle.mars

import com.akhutornoy.transformerswar.repository.cache.TransformerEntity
import com.akhutornoy.transformerswar.repository.rest.dto.Transformer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BattleInitializerTest {
    var battleInitializer = BattleInitializer()

    @Before
    fun init() {
        battleInitializer = BattleInitializer()
    }

    @Test fun `empty ListTransformers return NoFighters`() {
        val expectedFightersCount = 0
        val fighters = battleInitializer.disposeTransformers(arrayListOf())
        val actualFightersCount = fighters.size

        assertEquals(expectedFightersCount.toLong(), actualFightersCount.toLong())

    }

    @Test fun `two Transformers Within same Team return NoFighters`() {
        val expectedFightersCount = 0
        val a1 = TransformerEntity(team = Transformer.AUTOBOT_TEAM)
        val a2 = TransformerEntity(team = Transformer.AUTOBOT_TEAM)
        val transformers = arrayListOf(a1, a2)

        val fighters = battleInitializer.disposeTransformers(transformers)
        val actualFightersCount = fighters.size

        assertEquals(expectedFightersCount.toLong(), actualFightersCount.toLong())
    }

    @Test fun `two Different Team Transformers With Different "Rank" return "NoFighters"`() {
        val expectedFightersCount = 0
        val a = TransformerEntity(team = TransformerEntity.AUTOBOT_TEAM, rank = 1)
        val d = TransformerEntity(team = TransformerEntity.DECEPTICON_TEAM, rank = 2)
        val transformers = arrayListOf(a, d)

        val fighters = battleInitializer.disposeTransformers(transformers)
        val actualFightersCount = fighters.size

        assertEquals(expectedFightersCount.toLong(), actualFightersCount.toLong())
    }

    @Test fun `one Autobot And One Decepticon return One Fighters`() {
        val expectedFightersCount = 1

        val a = TransformerEntity(team = TransformerEntity.AUTOBOT_TEAM, rank = 1)
        val d = TransformerEntity(team = TransformerEntity.DECEPTICON_TEAM, rank = 1)
        val transformers = arrayListOf(a, d)

        val fighters = battleInitializer.disposeTransformers(transformers)
        val actualFightersCount = fighters.size

        assertEquals(expectedFightersCount.toLong(), actualFightersCount.toLong())
    }

    @Test fun `two Autobot And One Decepticon return One Fighters`() {
        val expectedFightersCount = 1

        val a1 = TransformerEntity(team = TransformerEntity.AUTOBOT_TEAM, rank = 1)
        val a2 = TransformerEntity(team = TransformerEntity.AUTOBOT_TEAM, rank = 1)
        val d = TransformerEntity(team = TransformerEntity.DECEPTICON_TEAM, rank = 1)
        val transformers = arrayListOf(a1, a2, d)

        val fighters = battleInitializer.disposeTransformers(transformers)
        val actualFightersCount = fighters.size

        assertEquals(expectedFightersCount.toLong(), actualFightersCount.toLong())
    }
}
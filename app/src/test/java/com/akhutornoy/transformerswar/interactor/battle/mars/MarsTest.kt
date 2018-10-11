package com.akhutornoy.transformerswar.interactor.battle.mars

import com.akhutornoy.transformerswar.repository.cache.TransformerEntity
import com.akhutornoy.transformerswar.ui.battle.model.BeforeBattleState
import com.akhutornoy.transformerswar.ui.battle.model.Fighters
import com.akhutornoy.transformerswar.ui.battle.model.Team
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MarsTest {
    private val OPTIMUS_PRIME = "Optimus Prime"
    private val PREDAKING = "Predaking"

    private lateinit var mars: Mars

    @Before fun init() {
        val battleInitializer = BattleInitializer()
        val calculator = RatingCalculator()
        val transformersArena = TransformersArena(calculator)
        mars = Mars(battleInitializer, transformersArena)
    }

    @Test fun `emptyList return NoTeam Winner Result`() {
        val expected = Team.NO_TEAM

        val fighters = arrayListOf<Fighters>()
        val beforeBattleState = BeforeBattleState(fighters)
        val afterBattleState = mars.startBattle(beforeBattleState)

        val actual = afterBattleState.winner
        assertEquals(expected, actual)
    }

    @Test fun `return Autobot Wins`() {
        val expected = Team.AUTOBOT
        val a = TransformerEntity(team = TransformerEntity.AUTOBOT_TEAM, strength = 2)
        val d = TransformerEntity(team = TransformerEntity.DECEPTICON_TEAM, strength = 1)
        val fighters = arrayListOf(Fighters(a, d, Fighters.FightResult.BOTH_ALIVE))
        val beforeBattleState = BeforeBattleState(fighters)

        val afterBattleState = mars.startBattle(beforeBattleState)

        val actual = afterBattleState.winner
        assertEquals(expected, actual)
    }

    @Test fun `return Decepticon Wins And Autobot Killed`() {
        val expectedTeam = Team.DECEPTICON
        val expectedAutobotsKilled = 1
        val expectedDecepticonssKilled = 0

        val a = TransformerEntity(team = TransformerEntity.AUTOBOT_TEAM, strength = 1)
        val d = TransformerEntity(team = TransformerEntity.DECEPTICON_TEAM, strength = 2)
        val fighters = arrayListOf(Fighters(a, d, Fighters.FightResult.BOTH_ALIVE))
        val beforeBattleState = BeforeBattleState(fighters)
        val afterBattleState = mars.startBattle(beforeBattleState)

        //who win battle
        val actual = afterBattleState.winner
        assertEquals(expectedTeam, actual)

        //check Decipticons killed number
        val actualDecipticonsKilled = afterBattleState.decepticonsKilled
        assertEquals(expectedDecepticonssKilled.toLong(), actualDecipticonsKilled.toLong())

        //check Autobot killed number
        val actualAutobotKilled = afterBattleState.autobotsKilled
        assertEquals(expectedAutobotsKilled.toLong(), actualAutobotKilled.toLong())
    }

    @Test fun `return Autobot Wins And Decepticons Killed`() {
        val expectedTeam = Team.AUTOBOT
        val expectedAutobotsKilled = 0
        val expectedDecepticonsKilled = 1

        val a = TransformerEntity(team = TransformerEntity.AUTOBOT_TEAM, strength = 2)
        val d = TransformerEntity(team = TransformerEntity.DECEPTICON_TEAM, strength = 1)
        val fighters = arrayListOf(Fighters(a, d, Fighters.FightResult.BOTH_ALIVE))
        val beforeBattleState = BeforeBattleState(fighters)
        val afterBattleState = mars.startBattle(beforeBattleState)

        //who wins battle
        val actual = afterBattleState.winner
        assertEquals(expectedTeam, actual)

        //check Decipticons killed number
        val actualDecipticonsKilled = afterBattleState.decepticonsKilled
        assertEquals(expectedDecepticonsKilled.toLong(), actualDecipticonsKilled.toLong())

        //check Autobot killed number
        val actualAutobotKilled = afterBattleState.autobotsKilled
        assertEquals(expectedAutobotsKilled.toLong(), actualAutobotKilled.toLong())
    }

    @Test fun `return Total Annihilation`() {
        val expectedTeam = Team.TOTAL_ANNIHILATION

        val a = TransformerEntity(team = TransformerEntity.AUTOBOT_TEAM, name = OPTIMUS_PRIME)
        val d = TransformerEntity(team = TransformerEntity.DECEPTICON_TEAM, name = OPTIMUS_PRIME)
        val fighters = arrayListOf(Fighters(a, d, Fighters.FightResult.BOTH_ALIVE))
        val beforeBattleState = BeforeBattleState(fighters)
        val afterBattleState = mars.startBattle(beforeBattleState)

        val actual = afterBattleState.winner
        assertEquals(expectedTeam, actual)
    }
}
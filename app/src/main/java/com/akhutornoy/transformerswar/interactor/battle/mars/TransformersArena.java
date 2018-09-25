package com.akhutornoy.transformerswar.interactor.battle.mars;

import android.support.annotation.Nullable;

import com.akhutornoy.transformerswar.repository.rest.dto.Transformer;
import com.akhutornoy.transformerswar.ui.battle.model.Fighters;
import com.akhutornoy.transformerswar.ui.battle.model.Fighters.FightResult;

public class TransformersArena {
    private static final String OPTIMUS_PRIME = "Optimus Prime";
    private static final String PREDAKING = "Predaking";

    private static final int GREAT_COURAGE = 4;
    private static final int GREAT_STRENGTH = 3;
    private static final int GREAT_SKILL = 3;

    private final RatingCalculator ratingCalculator;

    public TransformersArena(RatingCalculator ratingCalculator) {
        this.ratingCalculator = ratingCalculator;
    }

    public Fighters fight(Fighters fighters) {

        if (isTotalAnnihilation(fighters)) {
            return new Fighters(fighters.getAutobot(), fighters.getDecepticon(), FightResult.TOTAL_ANNIHILATION);
        }

        Transformer superTransformerVictory = applySuperTransformerBattle(fighters);
        if (superTransformerVictory != null) {
            return getFightersWithWinner(fighters, superTransformerVictory);
        }

        Transformer courageAndStrengthVictory = applyCourageAndStrengthBattle(fighters);
        if (courageAndStrengthVictory != null) {
            return getFightersWithWinner(fighters, courageAndStrengthVictory);
        }

        Transformer skillVictory = applySkillBattle(fighters);
        if (skillVictory != null) {
            return getFightersWithWinner(fighters, skillVictory);
        }

        Transformer overallRatingVictory = applyOverRatingBattle(fighters);
        if (overallRatingVictory != null) {
            return getFightersWithWinner(fighters, overallRatingVictory);
        }

        return new Fighters(fighters.getAutobot(), fighters.getDecepticon(), FightResult.BOTH_KILLED);
    }

    private Fighters getFightersWithWinner(Fighters fighters, Transformer winner) {
        FightResult winnerResult = getWinnerResultForTeam(winner.getTeam());
        return new Fighters(fighters.getAutobot(), fighters.getDecepticon(), winnerResult);
    }

    private FightResult getWinnerResultForTeam(String team) {
        switch (team) {
            case Transformer.AUTOBOT_TEAM:
                return FightResult.AUTOBOT_WINNER;
            case Transformer.DECEPTICON_TEAM:
                return FightResult.DECIPTICON_WINNER;
            default:
                throw new IllegalArgumentException(String.format("No WinnerFightResult for team=%s", team));
        }
    }

    /**
     * @return either winner or null
     */
    @Nullable
    private boolean isTotalAnnihilation(Fighters fighters) {
        Transformer autobot = fighters.getAutobot();
        Transformer decepticon = fighters.getDecepticon();

        boolean isAutobotSuperTransformer = OPTIMUS_PRIME.equals(autobot.getName())
                || PREDAKING.equals(autobot.getName());
        boolean isDeceptionSuperTransformer = OPTIMUS_PRIME.equals(decepticon.getName())
                || PREDAKING.equals(decepticon.getName());

        return isAutobotSuperTransformer && isDeceptionSuperTransformer;
    }

    /**
     * @return either winner or null
     */
    @Nullable
    private Transformer applySuperTransformerBattle(Fighters fighters) {
        Transformer autobot = fighters.getAutobot();
        Transformer decepticon = fighters.getDecepticon();

        if (OPTIMUS_PRIME.equals(autobot.getName())
                || PREDAKING.equals(autobot.getName())) {
            return autobot;
        }

        if (OPTIMUS_PRIME.equals(decepticon.getName())
                || PREDAKING.equals(decepticon.getName())) {
            return decepticon;
        }

        return null;
    }

    /**
     * @return either winner or null
     */
    @Nullable
    private Transformer applyCourageAndStrengthBattle(Fighters fighters) {
        Transformer autobot = fighters.getAutobot();
        Transformer decepticon = fighters.getDecepticon();
        int advantageByCourage = autobot.getCourage() - decepticon.getCourage();
        int advantageByStrength = autobot.getStrength() - decepticon.getStrength();

        boolean isGreatAdvantage = isGreatCourageAndStrengthAdvantage(advantageByCourage, advantageByStrength);
        if (isGreatAdvantage) {
            boolean isAdvantageBelongSameTransformer =
                    isAdvantageBelongSameTransformer(advantageByCourage, advantageByStrength);

            if (isAdvantageBelongSameTransformer) {
                boolean isAutobotWithGreatAdvantage = advantageByCourage > 0;
                return isAutobotWithGreatAdvantage ? autobot : decepticon;
            }
        }

        return null;
    }

    private boolean isGreatCourageAndStrengthAdvantage(int advantageByCourage, int advantageByStrength) {
        return Math.abs(advantageByCourage) >= GREAT_COURAGE
                && Math.abs(advantageByStrength) >= GREAT_STRENGTH;
    }

    private boolean isAdvantageBelongSameTransformer(int advantageByCourage, int advantageByStrength) {
        return (advantageByCourage > 0 && advantageByStrength > 0)
                || (advantageByCourage < 0 && advantageByStrength < 0);
    }

    /**
     * @return either winner or null
     */
    @Nullable
    private Transformer applySkillBattle(Fighters fighters) {
        Transformer autobot = fighters.getAutobot();
        Transformer decepticon = fighters.getDecepticon();
        int advantageBySkill = autobot.getSkill() - decepticon.getSkill();

        boolean isGreatAdvantage = Math.abs(advantageBySkill) >= GREAT_SKILL;
        if (isGreatAdvantage) {
            boolean isAutobotWithGreatAdvantage = advantageBySkill > 0;
            return isAutobotWithGreatAdvantage ? autobot : decepticon;
        }

        return null;
    }

    /**
     * @return either winner or null
     */
    @Nullable
    private Transformer applyOverRatingBattle(Fighters fighters) {
        Transformer autobot = fighters.getAutobot();
        Transformer decepticon = fighters.getDecepticon();
        int ratingAutobot = ratingCalculator.calculate(autobot);
        int ratingDeception = ratingCalculator.calculate(decepticon);

        if (ratingAutobot > ratingDeception) {
            return autobot;
        }

        if (ratingDeception > ratingAutobot) {
            return decepticon;
        }

        return null;
    }
}

package com.akhutornoy.transformerswar.repository.rest.dto;

public class Transformer {
    private int id;
    private String name;
    private String team;
    private String strength;
    private int intelligence;
    private int speed;
    private int endurance;
    private int rank;
    private int courage;
    private int firepower;
    private int skill;
    private String team_icon;

    public Transformer(int id, String name, String team, String strength, int intelligence, int speed, int endurance, int rank, int courage, int firepower, int skill, String team_icon) {
        this.id = id;
        this.name = name;
        this.team = team;
        this.strength = strength;
        this.intelligence = intelligence;
        this.speed = speed;
        this.endurance = endurance;
        this.rank = rank;
        this.courage = courage;
        this.firepower = firepower;
        this.skill = skill;
        this.team_icon = team_icon;
    }

    public int getId() {
        return id;
    }

    public String getTeam() {
        return team;
    }

    public String getStrength() {
        return strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getSpeed() {
        return speed;
    }

    public int getEndurance() {
        return endurance;
    }

    public int getRank() {
        return rank;
    }

    public int getCourage() {
        return courage;
    }

    public int getFirepower() {
        return firepower;
    }

    public int getSkill() {
        return skill;
    }

    public String getTeam_icon() {
        return team_icon;
    }


    public static class Builder {
        private int id;
        private String team;
        private String name;
        private String strength;
        private int intelligence;
        private int speed;
        private int endurance;
        private int rank;
        private int courage;
        private int firepower;
        private int skill;
        private String team_icon;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setTeam(String team) {
            this.team = team;
            return this;
        }

        public Builder setStrength(String strength) {
            this.strength = strength;
            return this;
        }

        public Builder setIntelligence(int intelligence) {
            this.intelligence = intelligence;
            return this;
        }

        public Builder setSpeed(int speed) {
            this.speed = speed;
            return this;
        }

        public Builder setEndurance(int endurance) {
            this.endurance = endurance;
            return this;
        }

        public Builder setRank(int rank) {
            this.rank = rank;
            return this;
        }

        public Builder setCourage(int courage) {
            this.courage = courage;
            return this;
        }

        public Builder setFirepower(int firepower) {
            this.firepower = firepower;
            return this;
        }

        public Builder setSkill(int skill) {
            this.skill = skill;
            return this;
        }

        public Builder setTeam_icon(String team_icon) {
            this.team_icon = team_icon;
            return this;
        }

        public Transformer createTransformer() {
            return new Transformer(id, name, team, strength, intelligence, speed, endurance, rank, courage, firepower, skill, team_icon);
        }
    }
}


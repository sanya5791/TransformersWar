package com.akhutornoy.transformerswar.repository.rest.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Transformer implements Parcelable {
    public static final String AUTOBOT_TEAM = "A";
    public static final String DECEPTICON_TEAM = "D";

    private String id;
    private String name;
    private String team;
    private int strength;
    private int intelligence;
    private int speed;
    private int endurance;
    private int rank;
    private int courage;
    private int firepower;
    private int skill;
    private String team_icon;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transformer that = (Transformer) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    public Transformer(String id, String name, String team, int strength, int intelligence, int speed, int endurance, int rank, int courage, int firepower, int skill, String team_icon) {
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

    protected Transformer(Parcel in) {
        id = in.readString();
        name = in.readString();
        team = in.readString();
        strength = in.readInt();
        intelligence = in.readInt();
        speed = in.readInt();
        endurance = in.readInt();
        rank = in.readInt();
        courage = in.readInt();
        firepower = in.readInt();
        skill = in.readInt();
        team_icon = in.readString();
    }

    public static final Creator<Transformer> CREATOR = new Creator<Transformer>() {
        @Override
        public Transformer createFromParcel(Parcel in) {
            return new Transformer(in);
        }

        @Override
        public Transformer[] newArray(int size) {
            return new Transformer[size];
        }
    };

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public int getStrength() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(team);
        dest.writeInt(strength);
        dest.writeInt(intelligence);
        dest.writeInt(speed);
        dest.writeInt(endurance);
        dest.writeInt(rank);
        dest.writeInt(courage);
        dest.writeInt(firepower);
        dest.writeInt(skill);
        dest.writeString(team_icon);
    }


    public static class Builder {
        private String id;
        private String team;
        private String name;
        private int strength;
        private int intelligence;
        private int speed;
        private int endurance;
        private int rank;
        private int courage;
        private int firepower;
        private int skill;
        private String team_icon;

        public Builder setId(String id) {
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

        public Builder setStrength(int strength) {
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

        public Transformer build() {
            return new Transformer(id, name, team, strength, intelligence, speed, endurance, rank, courage, firepower, skill, team_icon);
        }
    }
}


package com.xjob.persistence;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "user_skill")
@Entity(name = "user_skill")
@Getter
@Setter
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserSkill implements Serializable {
    private static final long serialVersionUID = 1234567L;
    @EmbeddedId
    private Id userSkillId;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("skillId")
    @JoinColumn(name = "skill_id", referencedColumnName = "skill_id")
    private Skill skill;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("uid")
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    private User user;

    public static class Id implements Serializable {

        private static final long serialVersionUID = 6234863104621942768L;
        @Column(name = "uid")
        private String uid;
        @Column(name = "skill_id")
        private Integer skillId;

        public Id() {
            super();
        }

        public Id(String uid, Integer skillId) {
            super();
            this.uid = uid;
            this.skillId = skillId;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public Integer getSkillId() {
            return skillId;
        }

        public void setSkillId(Integer skillId) {
            this.skillId = skillId;
        }

    }
}

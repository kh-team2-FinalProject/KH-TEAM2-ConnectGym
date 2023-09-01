package com.khteam2.connectgym.follow;

import com.khteam2.connectgym.member.Member;
import com.khteam2.connectgym.trainer.Trainer;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "follows", uniqueConstraints = {
    @UniqueConstraint(name = "uk_follow_from_to",columnNames = {"from_user_no", "to_trainer_no"})
})
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @ManyToOne
    @JoinColumn(name = "from_user_no")
    private Member fromUser;

    @ManyToOne
    @JoinColumn(name = "to_trainer_no")
    private Trainer toTrainer;
}

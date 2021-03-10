package org.antop.billiardslove.jpa.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.antop.billiardslove.exception.AlreadyParticipationException;
import org.antop.billiardslove.exception.PlayerNotFoundException;
import org.antop.billiardslove.jpa.convertor.ContestStateConverter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 대회 정보
 *
 * @author jammini
 */
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tbl_cnts")
public class Contest {
    /**
     * 대회 아이디
     */
    @Id
    @Column(name = "cnts_id")
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 대회명
     */
    @Setter
    @Column(name = "cnts_nm")
    private String title;
    /**
     * 대회 설명
     */
    @Setter
    @Column(name = "cnts_dscr")
    private String description;
    /**
     * 시작일
     */
    @Setter
    @Column(name = "strt_date")
    private LocalDate startDate;
    /**
     * 시작시간
     */
    @Setter
    @Column(name = "strt_time")
    private LocalTime startTime;
    /**
     * 종료일
     */
    @Setter
    @Column(name = "end_date")
    private LocalDate endDate;
    /**
     * 종료시간
     */
    @Setter
    @Column(name = "end_time")
    private LocalTime endTime;
    /**
     * 진행상태
     */
    @Setter
    @Convert(converter = ContestStateConverter.class)
    @Column(name = "prgr_stt")
    private State state = State.PREPARING;
    /**
     * 최대 참가 인원
     */
    @Setter
    @Column(name = "max_prtc_prsn")
    private Integer maximumParticipants;
    /**
     * 등록 일시
     */
    @CreatedDate
    @Column(name = "rgst_dt")
    private LocalDateTime created;
    /**
     * 마지막 수정 일시
     */
    @LastModifiedDate
    @Column(name = "mdfy_dt")
    private LocalDateTime modified;
    /**
     * 참가자 목록
     */
    @ToString.Exclude
    @OneToMany(mappedBy = "contest", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Player> players = new ArrayList<>();

    @Builder
    private Contest(String title) {
        this.title = title;
    }

    /**
     * 접수중 상태 여부
     *
     * @return 접수중
     */
    public boolean isAccepting() {
        return state == State.ACCEPTING;
    }

    /**
     * 대회에 회원을 참가시킨다.
     *
     * @param member   회원
     * @param handicap 참가 핸디캡
     * @throws org.antop.billiardslove.exception.MemberNotFountException 회원을 찾을 수 없을 경우
     * @throws AlreadyParticipationException                             이미 참여한 경우
     */
    public void participate(final Member member, final int handicap) {
        // 이미 참가한 회원인지 확인
        if (isParticipated(member)) {
            throw new AlreadyParticipationException();
        }

        Player player = Player.builder()
                .contest(this)
                .member(member)
                .handicap(handicap)
                .build();

        players.add(player);
    }

    /**
     * 회원이 대회에 참여한 상태인지 여부
     *
     * @param member 회원 정보
     * @return {@code true} 이미 참여
     */
    public boolean isParticipated(Member member) {
        return players.stream().anyMatch(it -> it.getMember() == member);
    }

    /**
     * 대회를 시작(재시작)할 수 있는 지 여부
     *
     * @return {@code true} 시작(재시작) 가능
     */
    public boolean canStart() {
        return state == State.ACCEPTING || state == State.STOPPED;
    }

    /**
     * 회원정보로 이 대회에 참가한 참가자 정보를 찾는다.
     *
     * @param member 회원 정보
     * @return {@link Player} 참가자 정보
     * @throws PlayerNotFoundException 참가자를 찾지 못했을 경우
     */
    public Player getPlayer(Member member) {
        return players.stream()
                .filter(p -> p.getMember() == member)
                .findFirst()
                .orElseThrow(PlayerNotFoundException::new);
    }

    /**
     * 대회 진행 상태
     *
     * @author jammini
     */
    @Getter
    @RequiredArgsConstructor
    public enum State {
        /**
         * 진행중
         */
        PROCEEDING("0"),
        /**
         * 접수중 (시작하지 않음)
         */
        ACCEPTING("1"),
        /**
         * 준비중
         */
        PREPARING("2"),
        /**
         * 중지됨
         */
        STOPPED("3"),
        /**
         * 종료
         */
        END("4");

        private final String code;

        public static State of(String code) {
            return Arrays.stream(values())
                    .filter(it -> it.code.equals(code))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
        }
    }

}

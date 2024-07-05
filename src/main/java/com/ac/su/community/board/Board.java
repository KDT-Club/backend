package com.ac.su.community.board;

@Entity
@Getter
@Setter
@ToString
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-incremnet
    @Column(name="board_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private BoardType boardType;
}
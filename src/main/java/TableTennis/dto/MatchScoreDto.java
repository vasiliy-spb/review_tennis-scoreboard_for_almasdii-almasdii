package TableTennis.dto;

public record MatchScoreDto(

        // Числительные в полях для счёта тоже можно писать словами (а не цифрами)

        String firstPlayerName,
        String secondPlayerName,
        int point1,
        int point2,
        int games1,
        int games2,
        int sets1,
        int sets2,
        boolean isTieBreakStarted,
        int firstPlayerTieBreakScore,
        int secondPlayerTieBreakScore
) {
}

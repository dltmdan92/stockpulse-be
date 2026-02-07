package com.stockpulse.api.config

import com.stockpulse.api.auth.entity.User
import com.stockpulse.api.auth.repository.UserRepository
import com.stockpulse.api.journal.entity.JournalEntry
import com.stockpulse.api.journal.repository.JournalEntryRepository
import com.stockpulse.api.stock.entity.Stock
import com.stockpulse.api.stock.repository.StockRepository
import com.stockpulse.api.trade.entity.Trade
import com.stockpulse.api.trade.entity.TradeType
import com.stockpulse.api.trade.repository.TradeRepository
import com.stockpulse.api.watchlist.entity.WatchlistItem
import com.stockpulse.api.watchlist.repository.WatchlistRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDate

@Component
@Profile("dev")
class DataInitializer(
    private val userRepository: UserRepository,
    private val stockRepository: StockRepository,
    private val tradeRepository: TradeRepository,
    private val journalEntryRepository: JournalEntryRepository,
    private val watchlistRepository: WatchlistRepository,
    private val passwordEncoder: PasswordEncoder,
) : ApplicationRunner {

    @Transactional
    override fun run(args: ApplicationArguments?) {
        if (userRepository.existsByEmail("demo@stockpulse.com")) return

        val user = userRepository.save(
            User(
                email = "demo@stockpulse.com",
                password = passwordEncoder.encode("demo1234"),
                name = "홍길동",
            )
        )
        val userId = user.id!!

        val stocks = stockRepository.saveAll(
            listOf(
                Stock(userId = userId, symbol = "AAPL", name = "Apple Inc.", quantity = 50, avgPrice = bd(175.5), currentPrice = bd(189.84), sector = "Technology", country = "US", addedAt = LocalDate.parse("2024-01-15")),
                Stock(userId = userId, symbol = "NVDA", name = "NVIDIA Corporation", quantity = 20, avgPrice = bd(450.0), currentPrice = bd(721.28), sector = "Technology", country = "US", addedAt = LocalDate.parse("2024-02-01")),
                Stock(userId = userId, symbol = "MSFT", name = "Microsoft Corporation", quantity = 30, avgPrice = bd(380.0), currentPrice = bd(415.5), sector = "Technology", country = "US", addedAt = LocalDate.parse("2024-01-20")),
                Stock(userId = userId, symbol = "005930", name = "삼성전자", quantity = 100, avgPrice = bd(72000.0), currentPrice = bd(75400.0), sector = "Technology", country = "KR", addedAt = LocalDate.parse("2024-01-10")),
                Stock(userId = userId, symbol = "AMZN", name = "Amazon.com Inc.", quantity = 25, avgPrice = bd(155.0), currentPrice = bd(178.25), sector = "Consumer Discretionary", country = "US", addedAt = LocalDate.parse("2024-02-05")),
                Stock(userId = userId, symbol = "JPM", name = "JPMorgan Chase & Co.", quantity = 40, avgPrice = bd(170.0), currentPrice = bd(195.8), sector = "Financials", country = "US", addedAt = LocalDate.parse("2024-01-25")),
            )
        )

        val trades = tradeRepository.saveAll(
            listOf(
                Trade(userId = userId, stockId = stocks[0].id, symbol = "AAPL", name = "Apple Inc.", type = TradeType.BUY, quantity = 50, price = bd(175.5), date = LocalDate.parse("2024-01-15"), memo = "AI 관련 기대감, 신규 제품 출시 예정. 장기 보유 목적.", tags = arrayOf("#장기투자", "#빅테크", "#AI"), targetPrice = bd(200.0), stopLoss = bd(160.0)),
                Trade(userId = userId, stockId = stocks[1].id, symbol = "NVDA", name = "NVIDIA Corporation", type = TradeType.BUY, quantity = 20, price = bd(450.0), date = LocalDate.parse("2024-02-01"), memo = "AI 반도체 시장 성장, 데이터센터 수요 급증 예상", tags = arrayOf("#AI", "#반도체", "#모멘텀"), targetPrice = bd(800.0), stopLoss = bd(400.0)),
                Trade(userId = userId, symbol = "TSLA", name = "Tesla Inc.", type = TradeType.BUY, quantity = 15, price = bd(220.0), date = LocalDate.parse("2024-01-05"), memo = "전기차 시장 확대, 저점 매수 기회", tags = arrayOf("#저점매수", "#전기차"), targetPrice = bd(280.0), stopLoss = bd(190.0)),
                Trade(userId = userId, symbol = "TSLA", name = "Tesla Inc.", type = TradeType.SELL, quantity = 15, price = bd(248.5), date = LocalDate.parse("2024-02-20"), memo = "목표가 달성 전 부분 익절. 시장 변동성 증가.", tags = arrayOf("#익절", "#전기차")),
            )
        )

        journalEntryRepository.saveAll(
            listOf(
                JournalEntry(userId = userId, tradeId = trades[0].id, date = LocalDate.parse("2024-01-15"), content = "Apple 매수 진입. AI 기대감과 신규 제품 라인업에 대한 긍정적 전망.", sentiment = "bullish"),
                JournalEntry(userId = userId, tradeId = trades[3].id, date = LocalDate.parse("2024-02-20"), content = "Tesla 익절 완료. 시장 변동성이 커지고 있어 리스크 관리 차원에서 정리.", sentiment = "neutral"),
            )
        )

        watchlistRepository.saveAll(
            listOf(
                WatchlistItem(userId = userId, symbol = "005930", name = "삼성전자", currentPrice = bd(75400.0), changePercent = bd(2.31), targetPrice = bd(85000.0), currency = "KRW"),
                WatchlistItem(userId = userId, symbol = "000660", name = "SK하이닉스", currentPrice = bd(178500.0), changePercent = bd(-1.38), targetPrice = bd(220000.0), currency = "KRW"),
                WatchlistItem(userId = userId, symbol = "035420", name = "NAVER", currentPrice = bd(214000.0), changePercent = bd(0.94), targetPrice = bd(250000.0), currency = "KRW"),
                WatchlistItem(userId = userId, symbol = "035720", name = "카카오", currentPrice = bd(48950.0), changePercent = bd(-2.10), targetPrice = bd(60000.0), currency = "KRW"),
                WatchlistItem(userId = userId, symbol = "373220", name = "LG에너지솔루션", currentPrice = bd(387000.0), changePercent = bd(1.57), targetPrice = bd(450000.0), currency = "KRW"),
            )
        )
    }

    private fun bd(value: Double): BigDecimal = BigDecimal.valueOf(value)
}

package step3

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@ExtendWith(MockKExtension::class)
class CarRacingTest {

    @MockK
    lateinit var movingStrategy: MovingStrategy

    @ParameterizedTest
    @CsvSource("3, 5",
        "4, 5",
        "3, 2")
    fun `1) 자동차 개수 생성 및 무조건 이동시 위치 테스트`(carCount: Int, moveCount: Int) {
        val carRacing = CarRacing.of(carCount, moveCount)
        every { movingStrategy.movable() } returns true
        carRacing.run(movingStrategy)
        for (i in 0 until carCount) {
            Assertions.assertThat(carRacing.cars.carsList[i].position).isEqualTo(moveCount)
        }
    }

    @ParameterizedTest
    @CsvSource("3, 5",
        "4, 5",
        "3, 2")
    fun `2) 자동차 개수 생성 및 MovingStrategyRandom 클래스 반영 테스트`(carCount: Int, moveCount: Int) {
        val carRacing = CarRacing.of(carCount, moveCount)
        carRacing.run(MovingStrategyRandom())
        Assertions.assertThat(carRacing.cars.carsList.size).isEqualTo(carCount)
    }
}

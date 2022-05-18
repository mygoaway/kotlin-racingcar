package racingcar

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import racingcar.domain.Car
import racingcar.domain.MovingStrategy

@ExtendWith(MockKExtension::class)
class CarTest {

    @MockK
    lateinit var movingStrategy: MovingStrategy

    @ParameterizedTest
    @ValueSource(strings = ["butter","audrey"])
    fun `1) 자동차 이름 5자 초과하는 경우 예외처리 테스트`(carName: String) {
        assertThrows<IllegalArgumentException> { Car.makeCar(carName) }
    }

    @ParameterizedTest
    @ValueSource(strings = ["jay","evan","kevin"])
    fun `2) 자동차 이름 저장 성공 + 이동 성공(랜덤 숫자가 4 초과하는 경우)`(carName: String) {
        val car = Car.makeCar(carName)
        every { movingStrategy.movable() } returns true
        car.move(movingStrategy)
        assertThat(car.position).isEqualTo(1)
        assertThat(car.name).isEqualTo(carName)
    }

    @ParameterizedTest
    @ValueSource(strings = ["jay","evan","kevin"])
    fun `3) 자동차 이름 저장 성공 + 이동 실패(랜덤 숫자가 4 초과하는 경우)`(carName: String) {
        val car = Car.makeCar(carName)
        every { movingStrategy.movable() } returns false
        car.move(movingStrategy)
        assertThat(car.position).isEqualTo(0)
        assertThat(car.name).isEqualTo(carName)
    }
}

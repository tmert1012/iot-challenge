import com.isidorefarm.uploader.entities.Reading
import java.util.ArrayList
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.Assertions.assertEquals

class ReadingTest {

    private val tests = arrayOf(
        // bad readings
        ReadingTest("bad int - letters", Reading("15353880as58212","73.23510","0.732911","104"), false),
        ReadingTest("bad int - special chars", Reading("153538*&8058212","73.23510","0.732911","104"), false),
        ReadingTest("bad double - letters", Reading("1535388058212","73.235df10","0.732911","104"), false),
        ReadingTest("bad double - spaces", Reading("1535388058212","73.23510","0.732  911","104"), false),

        // good readings
        ReadingTest("good reading 1", Reading("1535388058212","73.23510","0.732911","104"), true),
        ReadingTest("good reading 2", Reading("123456678","73.1233","0.8988998","199"), true),
    )

    @TestFactory
    fun dynamicReadingTests(): Collection<DynamicTest> {
        val dynamicTests = ArrayList<DynamicTest>()
        for (test in tests) {

            // check valid setting
            dynamicTests.add(dynamicTest(test.description) {
                assertEquals(
                    test.valid,
                    test.reading.valid()
                )
            })

        }
        return dynamicTests
    }


    internal class ReadingTest(val description: String, val reading: Reading, val valid: Boolean)

}
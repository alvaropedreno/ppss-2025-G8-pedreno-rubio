package ppss;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class DataArrayTest {

    @Test
    public void C1_delete_should_remove_first_occurrence_when_element_exists() throws DataException {
        DataArray dataArray = new DataArray(new int[]{1, 3, 5, 7});
        dataArray.delete(5);
        assertArrayEquals(new int[]{1, 3, 7}, dataArray.getColeccion());
        assertEquals(3, dataArray.size());
    }

    @Test
    public void C2_delete_should_remove_first_occurrence_when_multiple_instances_exist() throws DataException {
        DataArray dataArray = new DataArray(new int[]{1, 3, 3, 5, 7});
        dataArray.delete(3);
        assertArrayEquals(new int[]{1, 3, 5, 7}, dataArray.getColeccion());
        assertEquals(4, dataArray.size());
    }

    @Test
    public void C3_delete_should_remove_element_from_full_collection_when_element_exists() throws DataException {
        DataArray dataArray = new DataArray(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        dataArray.delete(4);
        assertArrayEquals(new int[]{1, 2, 3, 5, 6, 7, 8, 9, 10}, dataArray.getColeccion());
        assertEquals(9, dataArray.size());
    }

    @Test
    public void C4_delete_should_throw_exception_when_collection_is_empty() {
        DataArray dataArray = new DataArray(new int[]{});
        DataException exception = assertThrows(DataException.class, () -> dataArray.delete(8));
        assertEquals("No hay elementos en la colección", exception.getMessage());
    }

    @Test
    public void C5_delete_should_throw_exception_when_element_is_negative() {
        DataArray dataArray = new DataArray(new int[]{1, 3, 5, 7});
        DataException exception = assertThrows(DataException.class, () -> dataArray.delete(-5));
        assertEquals("El valor a borrar debe ser > 0", exception.getMessage());
    }

    @Test
    public void C6_delete_should_throw_exception_when_collection_is_empty_and_element_is_zero() {
        DataArray dataArray = new DataArray(new int[]{});
        DataException exception = assertThrows(DataException.class, () -> dataArray.delete(0));
        assertEquals("Colección vacía. Y el valor a borrar debe ser > 0", exception.getMessage());
    }

    @Test
    public void C7_delete_should_throw_exception_when_element_not_found() {
        DataArray dataArray = new DataArray(new int[]{1, 3, 5, 7});
        DataException exception = assertThrows(DataException.class, () -> dataArray.delete(8));
        assertEquals("Elemento no encontrado", exception.getMessage());
    }

    @ParameterizedTest(name = "delete_With_Exceptions_[{index}] Message exception should be \"{1}\" when we want delete {0}")
    @MethodSource("deleteWithExceptionsProvider")
    @DisplayName("delete_With_Exceptions_")
    @Tag("parametrizado")
    @Tag("conExcepciones")
    void C8_deleteWithExceptions(int valueToDelete, String expectedMessage, int[] initialArray) {
        DataArray dataArray = new DataArray(initialArray.clone()); // Clonamos para evitar modificarlo en otros tests

        Exception exception = assertThrows(DataException.class, () -> {
            dataArray.delete(valueToDelete);
        });

        assertEquals(expectedMessage, exception.getMessage());
    }

    static Stream<Arguments> deleteWithExceptionsProvider() {
        return Stream.of(
                Arguments.of(8, "No hay elementos en la colección", new int[]{}),
                Arguments.of(-5, "El valor a borrar debe ser > 0", new int[]{1, 2, 3}),
                Arguments.of(0, "Colección vacía. Y el valor a borrar debe ser > 0", new int[]{}),
                Arguments.of(8, "Elemento no encontrado", new int[]{1, 2, 3, 5, 7})
        );
    }

    @ParameterizedTest(name = "delete_Without_Exceptions_[{index}] should be {1} when we want delete {0}")
    @MethodSource("deleteWithoutExceptionsProvider")
    @DisplayName("delete_Without_Exceptions_")
    @Tag("parametrizado")
    void C9_deleteWithoutExceptions(int valueToDelete, int[] expectedArray, int[] initialArray) {
        DataArray dataArray = new DataArray(initialArray.clone());

        assertDoesNotThrow(() -> dataArray.delete(valueToDelete));

        assertArrayEquals(expectedArray, dataArray.getColeccion());
    }

    static Stream<Arguments> deleteWithoutExceptionsProvider() {
        return Stream.of(
                Arguments.of(5, new int[]{1, 3, 7}, new int[]{1, 3, 5, 7}),
                Arguments.of(3, new int[]{1, 5, 7}, new int[]{1, 3, 5, 7}),
                Arguments.of(4, new int[]{1,2,3,5,6,7,8,9,10}, new int[]{1,2,3,4,5,6,7,8,9,10})
        );
    }


}

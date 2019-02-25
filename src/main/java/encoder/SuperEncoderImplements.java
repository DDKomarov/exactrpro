package encoder;

import exception.CircularReference;
import exception.CyclicExceptionInCollections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SuperEncoderImplements implements SuperEncoder {

    private static final Logger LOGGER = LogManager.getLogger(SuperEncoderImplements.class.getName());
    private static List<String> listOfAllNamesOfCollections = new ArrayList<>();

    static {

        listOfAllNamesOfCollections.add("java.util.List");
        listOfAllNamesOfCollections.add("java.util.Set");
        listOfAllNamesOfCollections.add("java.util.Queue");
        listOfAllNamesOfCollections.add("java.util.Map");
    }


    @Override
    public byte[] serialize(Object anyBean) {
        checkNullReferenceObject(anyBean);
        checkCyclicReferences(anyBean);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(anyBean);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info(anyBean.getClass() + " was successfully serialized!");
        return byteArrayOutputStream.toByteArray();
    }


    private void checkNullReferenceObject(Object anyBean) {
        if (anyBean == null) {
            throw new NullPointerException();
        } else {
            LOGGER.info(anyBean.getClass() + " isn't null.");
        }
    }


    private void checkCyclicReferences(Object anyBean) {
        Field[] allClassFields = anyBean.getClass().getDeclaredFields();
        for (Field field : allClassFields) {
            if (anyBean.getClass().equals(field.getType())) {
                try {
                    throw new CircularReference();
                } catch (CircularReference e) {
                    e.printStackTrace();
                }
            }
            checkCyclicReferencesInCollections(anyBean, field);
        }
    }


    private void checkCyclicReferencesInCollections(Object anyBean, Field field) {
        for (String nameOfCollection : listOfAllNamesOfCollections) {
            if (field.getType().getName().equals(nameOfCollection)) {
                if (field.getGenericType().getTypeName().contains(anyBean.getClass().getName())) {
                    try {
                        throw new CyclicExceptionInCollections();
                    } catch (CyclicExceptionInCollections e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public Object deserialize(byte[] data) {
        checkNullSizeAndReference(data);
        Object object = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data));
            object = objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        checkNullReferenceObject(object);
        LOGGER.info("Deserialization succeeded!");
        return object;
    }

    private void checkNullSizeAndReference(byte[] data) {
        if (data == null || data.length == 0) {
            throw new NullPointerException();
        } else {
            LOGGER.info("Byte array isn't empty.");
        }
    }
}


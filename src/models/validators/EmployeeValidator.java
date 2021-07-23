package models.validators;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Employee;
import utils.DBUtil;

public class EmployeeValidator {
    public static List<String> validate(Employee e, Boolean codeDuplicateCheckFlag, Boolean passwordCheckFlag) {
        List<String> errors = new ArrayList<String>();

        String code_error = validateCode(e.getCode(), codeDuplicateCheckFlag);
        if(!code_error.equals("")) {
            errors.add(code_error);
        }

        String name_error = validateName(e.getName());
        if(!name_error.equals("")) {
            errors.add(name_error);
        }

        String password_error = validatePassword(e.getPassword(), passwordCheckFlag);
        if(!password_error.equals("")) {
            errors.add(password_error);
        }

        return errors;
    }

    // �Ј��ԍ�
    private static String validateCode(String code, Boolean codeDuplicateCheckFlag) {
        // �K�{���̓`�F�b�N
        if(code == null || code.equals("")) {
            return "�Ј��ԍ�����͂��Ă��������B";
        }

        // ���łɓo�^����Ă���Ј��ԍ��Ƃ̏d���`�F�b�N
        if(codeDuplicateCheckFlag) {
            EntityManager em = DBUtil.createEntityManager();
            long employees_count = (long)em.createNamedQuery("checkRegisteredCode", Long.class).setParameter("code", code).getSingleResult();
            em.close();
            if(employees_count > 0) {
                return "���͂��ꂽ�Ј��ԍ��̏��͂��łɑ��݂��Ă��܂��B";
            }
        }

        return "";
    }

    // �Ј����̕K�{���̓`�F�b�N
    private static String validateName(String name) {
        if(name == null || name.equals("")) {
            return "��������͂��Ă��������B";
        }

        return "";
    }

    // �p�X���[�h�̕K�{���̓`�F�b�N
    private static String validatePassword(String password, Boolean passwordCheckFlag) {
        // �p�X���[�h��ύX����ꍇ�̂ݎ��s
        if(passwordCheckFlag && (password == null || password.equals(""))) {
            return "�p�X���[�h����͂��Ă��������B";
        }
        return "";
    }
}




























package com.example.former.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.former.exception.ResourceNotFoundException;
import com.example.former.model.Field;
import com.example.former.model.Form;
import com.example.former.repository.FieldRepository;
import com.example.former.repository.FormRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FormService {

    private final FormRepository formRepository;

    public FormService(FormRepository formRepository) {
        this.formRepository = formRepository;
    }

    public List<Form> getAllForms() {
        return formRepository.findAll();
    }

    public Form getFormById(Long id) {
        return formRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Form not found with id " + id));
    }

    public Form createForm(Form form) {
        return formRepository.save(form);
    }

    public Form updateForm(Long id, Form formDetails) {
        Form form = formRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Form not found with id " + id));

        form.setName(formDetails.getName());
        form.setPublishStatus(formDetails.isPublishStatus());
        form.setMethod(formDetails.getMethod());
        form.setSubmitUrl(formDetails.getSubmitUrl());
        return formRepository.save(form);
    }

    public void deleteForm(Long id) {
        if (!formRepository.existsById(id)) {
            throw new ResourceNotFoundException("Form not found with id " + id);
        }
        formRepository.deleteById(id);
    }

    public List<Field> getFieldsByFormId(Long formId) {
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new ResourceNotFoundException("Form not found with id " + formId));
        return form.getFields();
    }

    public List<Field> updateFields(Long id, List<Field> fields) {
        Form form = formRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Form not found with id " + id));

        List<Field> updatedFields = form.getFields();
        for (Field field : fields) {
            Optional<Field> fieldToUpdate = updatedFields.stream()
                    .filter(existingField -> existingField.getId().equals(field.getId()))
                    .findFirst();

            if (fieldToUpdate.isPresent()) {
                Field existingField = fieldToUpdate.get();
                existingField.setName(field.getName());
                existingField.setLabel(field.getLabel());
                existingField.setType(field.getType());
                existingField.setDefaultValue(field.getDefaultValue());
            }
        }
        formRepository.save(form);
        return form.getFields();
    }

    public Form togglePublishStatus(Long formId) {
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new ResourceNotFoundException("Form not found with id " + formId));

        form.setPublishStatus(!form.isPublishStatus());
        return formRepository.save(form);
    }

    public List<Form> getPublishedForms() {
        return formRepository.findByPublishStatusTrue();
    }
}

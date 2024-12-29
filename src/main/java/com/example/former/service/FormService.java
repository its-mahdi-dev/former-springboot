package com.example.former.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.former.model.Field;
import com.example.former.model.Form;
import com.example.former.repository.FieldRepository;
import com.example.former.repository.FormRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FormService {

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private FieldRepository fieldRepository;

    public List<Form> getAllForms() {
        return formRepository.findAll();
    }

    public Optional<Form> getFormById(Long id) {
        return formRepository.findById(id);
    }

    public Form createForm(Form form) {
        return formRepository.save(form);
    }

    public Form updateForm(Long id, Form form) {
        if (!formRepository.existsById(id)) {
            return null;
        }
        form.setId(id);
        return formRepository.save(form);
    }

    public boolean deleteForm(Long id) {
        if (formRepository.existsById(id)) {
            formRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Field> getFieldsByFormId(Long formId) {
        Optional<Form> form = formRepository.findById(formId);
        return form.map(Form::getFields).orElse(null);
    }

    public void updateFields(Long formId, List<Field> fields) {
        Optional<Form> form = formRepository.findById(formId);
        form.ifPresent(f -> {
            f.setFields(fields);
            formRepository.save(f);
        });
    }

    public Form togglePublishStatus(Long formId) {
        Optional<Form> form = formRepository.findById(formId);
        if (form.isPresent()) {
            Form f = form.get();
            f.setPublishStatus(!f.isPublishStatus());
            return formRepository.save(f);
        }
        return null;
    }

    public List<Form> getPublishedForms() {
        return formRepository.findByPublishStatusTrue();
    }

    public Form save(Form form) {
        return formRepository.save(form); // Update form
    }
}

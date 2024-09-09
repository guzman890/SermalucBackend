package com.sermaluc.user.register.service.impl;

import com.sermaluc.user.register.model.dto.PhoneDTO;
import com.sermaluc.user.register.model.entity.PhoneEntity;
import com.sermaluc.user.register.repository.PhoneRepository;
import com.sermaluc.user.register.service.PhoneService;
import com.sermaluc.user.register.service.mapper.PhoneMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class PhoneServiceTest {

    @Mock
    private PhoneRepository phoneRepository;

    @InjectMocks
    private PhoneServiceImpl phoneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        PhoneEntity phoneEntity = new PhoneEntity();
        phoneEntity.setNumber("123456789");
        phoneEntity.setCityCode("01");
        phoneEntity.setContryCode("52");

        when(phoneRepository.findAll()).thenReturn(List.of(phoneEntity));

        List<PhoneDTO> phones = phoneService.findAll();

        assertEquals(1, phones.size());
        assertEquals(phoneEntity.getNumber(), phones.get(0).getNumber());
        assertEquals(phoneEntity.getCityCode(), phones.get(0).getCitycode());
        assertEquals(phoneEntity.getContryCode(), phones.get(0).getContrycode());

        verify(phoneRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Long phoneId = 1L;
        PhoneEntity phoneEntity = new PhoneEntity();
        phoneEntity.setId(phoneId);
        phoneEntity.setNumber("123456789");
        phoneEntity.setCityCode("01");
        phoneEntity.setContryCode("52");

        when(phoneRepository.findById(phoneId)).thenReturn(Optional.of(phoneEntity));

        Optional<PhoneDTO> phoneDTO = phoneService.findById(phoneId);

        assertTrue(phoneDTO.isPresent());
        assertEquals(phoneEntity.getNumber(), phoneDTO.get().getNumber());
        assertEquals(phoneEntity.getCityCode(), phoneDTO.get().getCitycode());
        assertEquals(phoneEntity.getContryCode(), phoneDTO.get().getContrycode());

        verify(phoneRepository, times(1)).findById(phoneId);
    }

    @Test
    void testSave() {
        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setNumber("123456789");
        phoneDTO.setCitycode("01");
        phoneDTO.setContrycode("52");

        PhoneEntity phoneEntity = PhoneMapper.toEntity(phoneDTO);
        phoneEntity.setUserId(1L);

        when(phoneRepository.save(any(PhoneEntity.class))).thenReturn(phoneEntity);

        PhoneDTO savedPhone = phoneService.save(phoneDTO, 1L);

        assertEquals(phoneDTO.getNumber(), savedPhone.getNumber());
        assertEquals(phoneDTO.getCitycode(), savedPhone.getCitycode());
        assertEquals(phoneDTO.getContrycode(), savedPhone.getContrycode());

        verify(phoneRepository, times(1)).save(any(PhoneEntity.class));
    }

    @Test
    void testDeleteById() {
        Long phoneId = 1L;

        doNothing().when(phoneRepository).deleteById(phoneId);

        phoneService.deleteById(phoneId);

        verify(phoneRepository, times(1)).deleteById(phoneId);
    }
}
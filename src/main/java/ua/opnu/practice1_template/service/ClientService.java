package ua.opnu.practice1_template.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.opnu.practice1_template.dto.ClientDto;
import ua.opnu.practice1_template.exception.ResourceNotFoundException;
import ua.opnu.practice1_template.model.Client;
import ua.opnu.practice1_template.repository.ClientRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientDto createClient(ClientDto clientDto) {
        Client client = convertToEntity(clientDto);
        Client savedClient = clientRepository.save(client);
        return convertToDto(savedClient);
    }

    public List<ClientDto> getAllClients() {
        return clientRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ClientDto getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        return convertToDto(client);
    }

    public ClientDto updateClient(Long id, ClientDto clientDto) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));

        existingClient.setName(clientDto.getName());
        existingClient.setPhone(clientDto.getPhone());
        existingClient.setEmail(clientDto.getEmail());

        Client updatedClient = clientRepository.save(existingClient);
        return convertToDto(updatedClient);
    }

    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        clientRepository.delete(client);
    }

    private Client convertToEntity(ClientDto clientDto) {
        Client client = new Client();
        client.setId(clientDto.getId());
        client.setName(clientDto.getName());
        client.setPhone(clientDto.getPhone());
        client.setEmail(clientDto.getEmail());
        return client;
    }

    private ClientDto convertToDto(Client client) {
        return new ClientDto(
                client.getId(),
                client.getName(),
                client.getPhone(),
                client.getEmail()
        );
    }
}
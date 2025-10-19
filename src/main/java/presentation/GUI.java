package presentation;

import db.ConnectionManager;
import domain.Apartment;
import domain.PurchaseRequest;
import domain.Client;
import service.ApartmentService;
import service.ClientService;
import service.PurchaseRequestService;
import service.ServiceException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class GUI {
    private Scanner scanner;
    private Map<Integer, Runnable> functions;
    private ApartmentService as;
    private PurchaseRequestService prs;
    private ClientService cs;

    public GUI (ConnectionManager cm) {
        this.scanner = new Scanner(System.in);
        this.as = new ApartmentService(cm);
        this.prs = new PurchaseRequestService(cm);
        this.cs = new ClientService(cm);
        initializeFunctions();
    }

    private void initializeFunctions() {
        functions = new HashMap<>();

        functions.put(1, this::viewAllApartments);
        functions.put(2, this::addApartment);
        functions.put(3, this::editApartment);
        functions.put(4, this::deleteApartment);
        functions.put(5, this::viewAllPurchaseRequests);
        functions.put(6, this::addPurchaseRequest);
        functions.put(7, this::editPurchaseRequest);
        functions.put(8, this::deletePurchaseRequest);

    }

    public void start() {
        System.out.println("КАРТОТЕКА АГЕНТСТВА НЕДВИЖИМОСТИ");
        while (true) {
            int i = 1;
            System.out.println("\nВыберите действие:\n" +
                    i++ + ". Просмотреть все квартиры\n" +
                    i++ + ". Добавить квартиру\n" +
                    i++ +". Редактировать квартиру\n" +
                    i++ + ". Удалить квартиру\n" +
                    i++ + ". Просмотреть все заявки на покупку\n" +
                    i++ + ". Добавить заявку на покупку\n" +
                    i++ + ". Редактировать заявку на покупку\n" +
                    i++ + ". Удалить заявку на покупку\n" +
                    "x - Выйти.\n");

            String inputStr = scanner.nextLine();
            if (inputStr.equals("x")) {
                break;
            }

            Integer input = null;
            try {
                input = Integer.parseInt(inputStr);
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод!");
                continue;
            }

            Runnable function = functions.get(input);
            if (function != null) {
                function.run();
            }
            else {
                System.out.println("Некорректный ввод!");
            }
        }
    }

    private void viewAllApartments() {
        List<Apartment> apartments = null;
        try {
            apartments = as.getAll();
        } catch (ServiceException e) {
            System.out.println("Произошла ошибка: " + e);
        }

        System.out.println("Все квартиры: ");
        if (apartments != null) {
            apartments.forEach(a -> System.out.println(a + "\n"));
        } else {
            System.out.println("Нет записей");
        }
    }

    private void addApartment() {
        System.out.println("ДОБАВЛЕНИЕ НОВОЙ КВАРТИРЫ");

        try {
            Apartment newApartment = inputApartment();

            List<PurchaseRequest> matchingRequests = as.createWithCheck(newApartment);

            if (matchingRequests.isEmpty()) {
                System.out.println("Квартира успешно добавлена!");
            } else {
                System.out.println("Найдены подходящие заявки на покупку, квартира НЕ была добавлена:");
                matchingRequests.forEach(pr -> System.out.println(pr + "\n"));
                System.out.println("Пожалуйста, свяжитесь с клиентами из заявок перед добавлением квартиры.");
            }

        } catch (ServiceException e) {
            System.out.println("Ошибка при добавлении квартиры: " + e.getMessage());
        }
    }

    private void editApartment() {
        System.out.println("РЕДАКТИРОВАНИЕ КВАРТИРЫ");

        try {
            // First show all apartments so user can see what's available
            List<Apartment> apartments = as.getAll();
            if (apartments == null || apartments.isEmpty()) {
                System.out.println("Нет квартир для редактирования.");
                return;
            }

            System.out.println("Список квартир:");
            for (int i = 0; i < apartments.size(); i++) {
                System.out.println((i + 1) + ". " + apartments.get(i) + "\n");
            }

            System.out.println("\nВведите номер квартиры для редактирования:");
            int apartmentIndex = inputInteger(1, apartments.size()) - 1;

            Apartment selectedApartment = apartments.get(apartmentIndex);
            System.out.println("Редактируемая квартира: " + selectedApartment);

            System.out.println("\nВведите новые данные (оставьте пустым для сохранения текущего значения):");

            System.out.println("Текущая улица: " + selectedApartment.getStreet());
            System.out.println("Новая улица:");
            String street = scanner.nextLine();
            if (!street.trim().isEmpty()) {
                selectedApartment.setStreet(street);
            }

            System.out.println("Текущий номер дома: " + selectedApartment.getBuilding());
            System.out.println("Новый номер дома:");
            String buildingInput = scanner.nextLine();
            if (!buildingInput.trim().isEmpty()) {
                selectedApartment.setBuilding(Integer.parseInt(buildingInput));
            }

            System.out.println("Текущий номер квартиры: " + selectedApartment.getNumber());
            System.out.println("Новый номер квартиры:");
            String numberInput = scanner.nextLine();
            if (!numberInput.trim().isEmpty()) {
                selectedApartment.setNumber(Integer.parseInt(numberInput));
            }

            System.out.println("Текущее количество комнат: " + selectedApartment.getRoomCount());
            System.out.println("Новое количество комнат:");
            String roomCountInput = scanner.nextLine();
            if (!roomCountInput.trim().isEmpty()) {
                selectedApartment.setRoomCount(Integer.parseInt(roomCountInput));
            }

            System.out.println("Текущая площадь: " + selectedApartment.getArea());
            System.out.println("Новая площадь:");
            String areaInput = scanner.nextLine();
            if (!areaInput.trim().isEmpty()) {
                selectedApartment.setArea(Integer.parseInt(areaInput));
            }

            System.out.println("Текущая цена: " + (selectedApartment.getPrice() / 100.0));
            System.out.println("Новая цена:");
            String priceInput = scanner.nextLine();
            if (!priceInput.trim().isEmpty()) {
                selectedApartment.setPrice((int)(Float.parseFloat(priceInput) * 100));
            }

            // Update the apartment
            boolean success = as.update(selectedApartment);
            if (success) {
                System.out.println("Квартира успешно обновлена!");
            } else {
                System.out.println("Не удалось обновить квартиру.");
            }

        } catch (ServiceException e) {
            System.out.println("Ошибка при редактировании квартиры: " + e.getMessage());
        }
    }

    private void deleteApartment() {
        System.out.println("УДАЛЕНИЕ КВАРТИРЫ");

        try {
            // First show all apartments so user can see what's available
            List<Apartment> apartments = as.getAll();
            if (apartments == null || apartments.isEmpty()) {
                System.out.println("Нет квартир для удаления.");
                return;
            }

            System.out.println("Список квартир:");
            for (int i = 0; i < apartments.size(); i++) {
                System.out.println((i + 1) + ". " + apartments.get(i) + "\n");
            }

            System.out.println("\nВведите номер квартиры для удаления:");
            int apartmentIndex = inputInteger(1, apartments.size()) - 1;

            Apartment selectedApartment = apartments.get(apartmentIndex);
            System.out.println("Выбранная квартира для удаления: " + selectedApartment);

            System.out.println("Вы уверены, что хотите удалить эту квартиру? (y/n)");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("y")) {
                boolean success = as.delete(selectedApartment.getId());
                if (success) {
                    System.out.println("Квартира успешно удалена!");
                } else {
                    System.out.println("Не удалось удалить квартиру.");
                }
            } else {
                System.out.println("Удаление отменено.");
            }

        } catch (ServiceException e) {
            System.out.println("Ошибка при удалении квартиры: " + e.getMessage());
        }
    }


    private void viewAllPurchaseRequests() {
        System.out.println("ПРОСМОТР ВСЕХ ЗАЯВОК НА ПОКУПКУ");

        try {
            List<PurchaseRequest> purchaseRequests = prs.getAll();

            System.out.println("Все заявки на покупку: ");
            if (purchaseRequests != null && !purchaseRequests.isEmpty()) {
                purchaseRequests.forEach(pr -> System.out.println(pr + "\n"));
            } else {
                System.out.println("Нет заявок на покупку");
            }

        } catch (ServiceException e) {
            System.out.println("Ошибка при получении заявок на покупку: " + e.getMessage());
        }
    }

    private void addPurchaseRequest() {
        System.out.println("ДОБАВЛЕНИЕ НОВОЙ ЗАЯВКИ НА ПОКУПКУ");

        try {
            // First, handle client selection/creation
            Client client = selectOrCreateClient();
            if (client == null) {
                System.out.println("Операция отменена.");
                return;
            }

            // Then input purchase request details
            PurchaseRequest newRequest = inputPurchaseRequest(client);

            // Use createWithCheck to see if there are matching apartments
            List<Apartment> matchingApartments = prs.createWithCheck(newRequest);

            if (matchingApartments.isEmpty()) {
                System.out.println("Заявка на покупку успешно добавлена!");
            } else {
                System.out.println("Найдены подходящие квартиры, заявка НЕ была добавлена:");
                matchingApartments.forEach(apt -> System.out.println(apt + "\n"));
                System.out.println("Пожалуйста, свяжитесь с клиентом по поводу найденных квартир.");
            }

        } catch (ServiceException e) {
            System.out.println("Ошибка при добавлении заявки на покупку: " + e.getMessage());
        }
    }

    private Client selectOrCreateClient() throws ServiceException {
        System.out.println("\nВыберите клиента:");
        System.out.println("1. Выбрать из существующих клиентов");
        System.out.println("2. Добавить нового клиента");
        System.out.println("0. Отмена");

        int choice = inputInteger(0, 2);

        switch (choice) {
            case 1:
                return selectExistingClient();
            case 2:
                return createNewClient();
            default:
                return null;
        }
    }

    private Client selectExistingClient() throws ServiceException {
        List<Client> clients = cs.getAll();
        if (clients == null || clients.isEmpty()) {
            System.out.println("Нет существующих клиентов. Создайте нового клиента.");
            return createNewClient();
        }

        System.out.println("\nСписок клиентов:");
        for (int i = 0; i < clients.size(); i++) {
            System.out.println((i + 1) + ". " + clients.get(i));
        }

        System.out.println("\nВведите номер клиента:");
        int clientIndex = inputInteger(1, clients.size()) - 1;

        return clients.get(clientIndex);
    }

    private Client createNewClient() throws ServiceException {
        System.out.println("\nДОБАВЛЕНИЕ НОВОГО КЛИЕНТА");

        System.out.println("Введите полное имя клиента:");
        String fullName = scanner.nextLine();

        System.out.println("Введите номер телефона (формат: +375 XX XXX XX XX):");
        String phoneNumber = scanner.nextLine();

        try {
            Client newClient = new Client(fullName, phoneNumber);
            boolean success = cs.create(newClient);
            if (success) {
                System.out.println("Клиент успешно добавлен!");
                return newClient;
            } else {
                throw new ServiceException("Не удалось создать клиента");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка в данных клиента: " + e.getMessage());
            return createNewClient(); // Retry
        }
    }

    private PurchaseRequest inputPurchaseRequest(Client client) {
        System.out.println("\nВведите данные заявки на покупку:");

        System.out.println("Введите желаемое количество комнат:");
        int roomCount = inputInteger(PurchaseRequest.MIN_ROOM_COUNT, PurchaseRequest.MAX_ROOM_COUNT);

        System.out.println("Введите минимальную площадь:");
        int minArea = inputInteger(PurchaseRequest.MIN_AREA, PurchaseRequest.MAX_AREA);

        System.out.println("Введите максимальную площадь:");
        int maxArea = inputInteger(minArea, PurchaseRequest.MAX_AREA);

        System.out.println("Введите минимальную цену (в USD):");
        int minPrice = inputPrice(PurchaseRequest.MIN_PRICE, PurchaseRequest.MAX_PRICE);

        System.out.println("Введите максимальную цену (в USD):");
        int maxPrice = inputPrice(minPrice, PurchaseRequest.MAX_PRICE);

        return new PurchaseRequest(
                roomCount,
                minArea,
                maxArea,
                minPrice,
                maxPrice,
                client
        );
    }

    private void editPurchaseRequest() {
        System.out.println("РЕДАКТИРОВАНИЕ ЗАЯВКИ НА ПОКУПКУ");

        try {
            // First show all purchase requests so user can see what's available
            List<PurchaseRequest> purchaseRequests = prs.getAll();
            if (purchaseRequests == null || purchaseRequests.isEmpty()) {
                System.out.println("Нет заявок на покупку для редактирования.");
                return;
            }

            System.out.println("Список заявок на покупку:");
            for (int i = 0; i < purchaseRequests.size(); i++) {
                System.out.println((i + 1) + ". " + purchaseRequests.get(i) + "\n");
            }

            System.out.println("\nВведите номер заявки для редактирования:");
            int requestIndex = inputInteger(1, purchaseRequests.size()) - 1;

            PurchaseRequest selectedRequest = purchaseRequests.get(requestIndex);
            System.out.println("Редактируемая заявка: " + selectedRequest);

            System.out.println("\nВведите новые данные (оставьте пустым для сохранения текущего значения):");

            System.out.println("Текущее количество комнат: " + selectedRequest.getRoomCount());
            System.out.println("Новое количество комнат:");
            String roomCountInput = scanner.nextLine();
            if (!roomCountInput.trim().isEmpty()) {
                selectedRequest.setRoomCount(Integer.parseInt(roomCountInput));
            }

            System.out.println("Текущая минимальная площадь: " + selectedRequest.getMinArea());
            System.out.println("Новая минимальная площадь:");
            String minAreaInput = scanner.nextLine();
            if (!minAreaInput.trim().isEmpty()) {
                selectedRequest.setMinArea(Integer.parseInt(minAreaInput));
            }

            System.out.println("Текущая максимальная площадь: " + selectedRequest.getMaxArea());
            System.out.println("Новая максимальная площадь:");
            String maxAreaInput = scanner.nextLine();
            if (!maxAreaInput.trim().isEmpty()) {
                selectedRequest.setMaxArea(Integer.parseInt(maxAreaInput));
            }

            System.out.println("Текущая минимальная цена: " + (selectedRequest.getMinPrice() / 100.0) + " BYN");
            System.out.println("Новая минимальная цена:");
            String minPriceInput = scanner.nextLine();
            if (!minPriceInput.trim().isEmpty()) {
                selectedRequest.setMinPrice((int)(Float.parseFloat(minPriceInput) * 100));
            }

            System.out.println("Текущая максимальная цена: " + (selectedRequest.getMaxPrice() / 100.0) + " BYN");
            System.out.println("Новая максимальная цена:");
            String maxPriceInput = scanner.nextLine();
            if (!maxPriceInput.trim().isEmpty()) {
                selectedRequest.setMaxPrice((int)(Float.parseFloat(maxPriceInput) * 100));
            }

            // Update the purchase request
            boolean success = prs.update(selectedRequest);
            if (success) {
                System.out.println("Заявка на покупку успешно обновлена!");
            } else {
                System.out.println("Не удалось обновить заявку на покупку.");
            }

        } catch (ServiceException e) {
            System.out.println("Ошибка при редактировании заявки на покупку: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка в данных: " + e.getMessage());
        }
    }

    private void deletePurchaseRequest() {
        System.out.println("УДАЛЕНИЕ ЗАЯВКИ НА ПОКУПКУ");

        try {
            // First show all purchase requests so user can see what's available
            List<PurchaseRequest> purchaseRequests = prs.getAll();
            if (purchaseRequests == null || purchaseRequests.isEmpty()) {
                System.out.println("Нет заявок на покупку для удаления.");
                return;
            }

            System.out.println("Список заявок на покупку:");
            for (int i = 0; i < purchaseRequests.size(); i++) {
                System.out.println((i + 1) + ". " + purchaseRequests.get(i) + "\n");
            }

            System.out.println("\nВведите номер заявки для удаления:");
            int requestIndex = inputInteger(1, purchaseRequests.size()) - 1;

            PurchaseRequest selectedRequest = purchaseRequests.get(requestIndex);
            System.out.println("Выбранная заявка для удаления: " + selectedRequest);

            System.out.println("Вы уверены, что хотите удалить эту заявку? (y/n)");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("y")) {
                boolean success = prs.delete(selectedRequest.getId());
                if (success) {
                    System.out.println("Заявка на покупку успешно удалена!");
                } else {
                    System.out.println("Не удалось удалить заявку на покупку.");
                }
            } else {
                System.out.println("Удаление отменено.");
            }

        } catch (ServiceException e) {
            System.out.println("Ошибка при удалении заявки на покупку: " + e.getMessage());
        }
    }


    private Apartment inputApartment() {
        System.out.println("Введите улицу:");
        String street = scanner.nextLine();

        System.out.println("Введите номер дома:");
        int building = inputInteger(Apartment.MIN_BUILDING, Apartment.MAX_BUILDING);

        System.out.println("Введите номер квартиры:");
        int number = inputInteger(Apartment.MIN_NUMBER, Apartment.MAX_NUMBER);

        System.out.println("Введите количество комнат");
        int roomCount = inputInteger(Apartment.MIN_ROOM_COUNT,Apartment.MAX_ROOM_COUNT);

        System.out.println("Введите площадь:");
        int area = inputInteger(Apartment.MIN_AREA, Apartment.MAX_AREA);

        System.out.println("Введите цену:");
        int price = inputPrice(Apartment.MIN_PRICE, Apartment.MAX_PRICE);

        return new Apartment(
                street,
                building,
                number,
                roomCount,
                area,
                price
        );
    }

    private int inputInteger(int min, int max) {
        Integer input = null;
        while (true) {
            try {
                input = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Введите число!");
                continue;
            }

            if (input < min || input > max) {
                System.out.printf("Число должно быть между %d и %d!\n", min, max);
                continue;
            }

            return input;
        }
    }

    public int inputPrice(int min, int max) {
        Integer input = null;
        while (true) {
            try {
                input = (int) Float.parseFloat(scanner.nextLine()) * 100;
            } catch (NumberFormatException e) {
                System.out.println("Введите число!");
                continue;
            }

            if (input < min || input > max) {
                System.out.printf("Число должно быть между %d и %d!\n", min / 100, max / 100);
                continue;
            }

            return input;
        }
    }
}

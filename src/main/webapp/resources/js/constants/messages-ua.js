/**
 * Created by Vitalij Fedyna on 19.11.16.
 */
const messages = {
    kid: {
        invalidFirstName: 'Ви ввели ім\'я невірно',
        invalidLastName: 'Ви ввели прізвище невірно',
        requiredFirstName: 'Вкажіть будь ласка ім\'я дитини',
        requiredLastName: 'Вкажіть будь ласка прізвище дитини',
        regexKidFirstName: 'Для вводу імені використовуйте лише літери',
        regexKidLastName: 'Для вводу прізвища використовуйте лише літери',

        toShortFirstName: 'Будь ласка, введіть не менше ' + constants.parameters.nameMinLength + 'символів.',
        toShortLastName: 'Будь ласка, введіть не менше ' + constants.parameters.nameMinLength + ' символів.',
        toLongFirstName: 'Будь ласка, введіть менше ' + constants.parameters.nameMaxLength + ' символів.',
        toLongLastName: 'Будь ласка введіть менше ' + constants.parameters.nameMaxLength + ' символів.',

        editFirstName: 'Будь ласка, введіть не менше 2 символів.',
        editLastName: 'Будь ласка, введіть не менше 2 символів.',
        editFirstNameWithDigits: 'Ім\'я не може складатись із чисел',
        editLastNameWithDigits: 'Прізвище не може складатись із чисел'
    },
    date: {
        fromBiggerThanTo: 'Початкова дата не повинна перевищувати кінцеву',
        startEventDate: 'Початок: ',
        endEventDate: 'Кінець: '
    },
    info: {
        description: 'Опис: ',
        noDescription: 'Ця подія немає опису',
        fromBiggerThanTo: 'Початкова дата не повинна перевищувати кінцеву'
    },
    dateTable: {
        emptyTable: 'Немає даних в таблиці',
        processing: 'Зачекайте...',
        search: 'Пошук:',
        lengthMenu: 'Показати _MENU_ записів',
        info: 'Записи з _START_ по _END_ із _TOTAL_ записів',
        infoEmpty: 'Записи з 0 по 0 із 0 записів',
        infoFiltered: '(відфільтровано з _MAX_ записів)',
        loadingRecords: 'Завантаження...',
        zeroRecords: 'Записи відсутні',
        paginate: {
            first: 'Перша',
            previous: 'Попередня',
            next: 'Наступна',
            last: 'Остання'
        },
        aria: {
            sortAscending: ': активувати для сортування стовпців за зростанням',
            sortDescending: ': активувати для сортування стовпців за спаданням'
        }
    },
    modal: {
        kid: {
            comment: 'Коментарій',
            choose: 'Будь ласка виберіть дитину'
        }
    },
    event: {
        errors: {
            titleFieldRequired: 'Поле назва повинно бути заповнене',
            titleMaximumCharacters: 'Назва не повинна містити більше ' + constants.parameters.titleMaxLenght + ' символів',
            descriptionMaximumCharacters: 'Поле опису не повинно містити  більше ' + constants.parameters.descriptionMaxLenght + ' символів',
            startDateFormat: 'Використовуйте правильний формат yyyy-mm-dd HH:MM для дати початку',
            endDateFormat: 'Використовуйте правильний формат yyyy-mm-dd HH:MM для дати кінця',
            singleEventDateEquals:  'Для одноденної події дати повинні бути рівні',
            invalidColor: 'Невірно вибраний колір',
            invalidDate: 'Невірно введена дата',
            invalidTime: 'Невірно введений час',
            dateInThePast: 'Дата не може бути в минулому, поточна дата: ',
            timeInThePast: 'Час початку не може бути в минулому, поточний час: ',
            endTimeGreaterThanStartTime: 'Час закінчення повинен бути хоча б на хвилину більший за час початку',
            minimalDatesDifference: 'Повторювані: Дата закінчення повинна бути хоча б на день більшою за дату початку',
            emptyTitle: 'Назва повинна бути заповнена',
            noDaysSelected: 'Повторювані: Як мінімум один день повинен бути вибраним',
            noKidsSelected: 'Як мінімум одна дитина повинна бути вибрана',
            bookingTypeMismatchWhenUpdating: 'Неможливо перетворити щотижневе бронювання в одноденне',
            eventTypeMismatchWhenUpdating: 'Неможливо змінити тип події',
            incorrectData: 'Невірні дані: ',
            roomDeactivate: 'Кімната не може бути деактивованою, оскільки вона має зареєстровані майбутні події. Будь ласка, сконтактуйтесь з менеджером.',
            dateDoesntExits: 'Вказаної дати не має у календарі, будь ласка, оберіть інакшу',
            cannotCreateEventsForNonExistingDates: 'Не вдалось створити події на певні дати, так як їх не має у календарі'
        }
    },
    notCorrect: {
        time: '<b>невідомо</b>. Введено неправильний час. Допустимий формат: <b>HH:mm</b>',
        startDate: 'Введено неправильну початкову дату. Допустимий формат: <b>dd:MM:YYYY</b>',
        endDate: 'Введено неправильну кінцеву дату. Допустимий формат: <b>dd:MM:YYYY</b>',
        server: '<b>невідомо</b>. Помилка сервера',
        emptyStartDate: 'Не введено початкову дату',
        emptyEndDate: 'Не введено кінцеву дату',
        pastStartDay: 'Початкова дата на може бути в минулому',
        pastEndDay: 'Кінцева дата не може бути меншою за початкову',
        wrongDateStartRange: 'Початкова дата є поза межами вибраних заходів',
        wrongDateEndRange: 'Кінцева дата є поза межами вибраних заходів'
    },
    room: {
        errors: {
            invalidName: 'Невірно заповнене поле Назва',
            invalidAdress: 'Невірно заповнене поле Адрес',
            invalidCity: 'Невірно заповнене поле Місто',
            invalidPhone: 'Невірно заповнене поле Номер телефону',
            requiredWithEmptySpace: 'Поле потрібно заповнити'
        },
        warnings: {
            active: 'Попередження! У кімнаті перебувають діти.',
            planning: 'Попередження! У кімнаті є здійсненні бронювання.'
        }
    },
    adminValidation: {
        email: 'Некоректний емеіл',
        firstName: 'Ім\'я введено невірно',
        lastName: 'Призвище введено невірно',
        phone: 'Номер телефону є невірним. Приклад: +380991234567',
        required: 'Поле є пустим. Будьласка заповніть поле.'
    },
    booking: {
        hint: {
            leaveTime: 'Натисніть, щоб встановити час прибуття',
            arrivedTime: 'Натисніть, щоб встановити час виходу'
        }
    }
};

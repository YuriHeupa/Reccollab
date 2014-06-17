package processing.app;


public class Lang {
	public static enum LANGUAGES {
		PORTUGUES,
		ENGLISH,
	}

	public static String[] avaiableLangs() {
		String[] tmp = new String[LANGUAGES.values().length];

		for (int i = 0; i < LANGUAGES.values().length; i++) {
			tmp[i] = LANGUAGES.values()[i].toString();
		}

		return tmp;
	}

	public static void setLanguage(LANGUAGES lang) {
		switch (lang) { 
		case ENGLISH:
			CLICK_TO_GO = "Click to go to folder";
			CLICK_ANYWHERE = "(Click anywhere to dismiss)\n The modules can be activated down here";
			YOU_ARE_CONNECTED_AS = "You are connect as:";
			SELECT_LANGUAGE = "Select language:";
			THERES_NO_USER_SELECTED = "There's no user selected";
			LOGIN = "Login";
			CHANGE_USER = "Change user";
			ABOUT = "About";
			LANGUAGE_CHANGE_TITLE = "Language change";
			LANGUAGE_CHANGE_WARNING = "The language will only change\n"+
					"in the next program startup. \n";
			CREATE_USER = "Create user";
			INITIALIZING = "Starting";
			SETTING_APP_CONFIG = "Setting the application configuration";
			LOADING_CONFIG_FILE = "Loading configuration file";
			LOADING_IMAGES = "Loading images";
			VALIDATING_USER_DATA = "Validating user data";
			INITIALIZING_WEBCAM = "Starting video capture system";
			INITIALIZING_PROCESS = "Starting process capture system";
			INITIALIZING_SCREENSHOT = "Starting image capture systemm";
			INITIALIZING_FILECHANGE = "Starting file change system";
			INITIALIZING_HELP = "Starting help system";
			INITIALIZING_IO = "Starting communication system";
			INITIALIZING_ENCODER = "Starting encoder";
			INITIALIZING_INTERFACE = "Starting interface";
			LOADING_DEFAULT_CONFIG = "Loading default configuration";
			SETTING_FINAL_CONFIG = "Setting final configuration";
			APP_SUCCESS_LOAD = "Application loaded with success!";
			ARE_YOU_SURE_TO_EXIT = "Are you sure you want to exit?";
			EXIT_CONFIRMATION_TITLE = "Exit confimation";

			CONFIGURE = "Configure";
			CONFIGURATION = "CONFIGURATION";
			ADVANCED = "ADVANCED (BETA)";
			MOUSE_TRACK = "Mouse track";
			KEYBOARD_STATICS = "Keyboard Statistics";
			FILE_CHANGE = "File change";
			PROCESS_OPENED = "Open programs";
			IMPORT = "Import";
			EXPORT = "Export";
			SELECT_EXPORT_FOLDER = "Select a folder to export:";
			EXPORTING = "Exporting, wait...";
			CAPTURE_TAB = "Capture";
			TREAT_IMAGE_TAB = "Treat images";
			VISUALIZATION_TAB = "Visualizations";
			STATICS = "Statistics";
			KEYBOARD = "Keyboard";
			FILES = "File Change";
			PROCESS = "Programs";
			TYPE_USER = "Type your name";
			NEED_MIN_3_CHARS = "Use a minimum of 3 characters";
			ONLY_CHAR_AND_NUMBERS = "The user name should only contain letters and numbers";
			BACK = "Back";
			ABOUT_DESC = "Created as a research project by Yuri Heupa "+
					"under the guidance of Bruno Campagnolo, from resources of " + 
					"Funda��o Arauc�ria. Version 1.3";
			VIDEO = "Video";
			WARNING_AREA = "BILLBOARD";
			WARNING_AREA_EXPAND = "BILLBOARD: (Click to expand)";
			TIME_CAPTURE_SEC = "Capture time in seconds:";
			SAVE_PATH = "Save folder:";
			SEARCH = "Search";
			FILES_MOVED_TITLE = "Files moved";
			FILES_MOVED_MESSAGE = "The files have been moved from";
			TO = "to";
			CONFIRM_CHANGES_TITLE = "Confirm changes";
			CONFIRM_CHANGES_MESSAGE = "You have unsaved changes, you want to save?";
			SELECT_SAVE_FOLDER = "Select a folder to save:";
			SELECT_FOLDER = "Select a folder:";
			CAMERA_SELECTION = "Camera selecion:";
			SAVE = "Save";
			CAPTURE_MOUSE_MOVEMENT = "Capture mouse movement:";
			CAPTURE_MOUSE_CLICK = "Capture mouse clicks";
			WORDS_TYPED = "Words typed";
			WORDS_PM = "Words per minute";
			KEYS_TYPED = "Keys tped";
			KEYS_PM = "Keys per minute";
			KEYBOARD_SECURITY_WARNING = "ATTENTION! For your safety always disable when typing passwords";
			SELECT_WATCH_FOLDER = "Select a folder to be monitored:";
			WATCH_FOLDER = "Folder being monitored:";
			IMAGES_NUMBER = "Images number:";
			RESOLUTION = "Resolution:";
			FOLDER = "Folder:";
			LAST_IMAGE = "Last image:";
			NO_IMAGE_CAPTURED = "Not yet captured images";
			CREATED = "Created:";
			MODIFIED = "Modified:";
			DELETED = "Deleted:";
			DATA_SIZE = "Data size:";
			CLICKS_NUMBER = "Clicks number:";
			DISTANCE_TRAVEL = "Distance travel:";
			MORE_USED = "More used:";
			WORDS_NUMBER = "Words number:";
			LAST_WORDS = "Last words:";
			KEYS_NUMBER = "Keys number:";
			LAST_KEYS = "Last keys:";
			WORDS_PM_STATIC = "Words/Minute:";
			KEYS_PM_STATIC = "Keys/Minute:";
			UNDEFINED = "Undefined";
			IMAGES = "Images:";
			FORMAT = "Format:";
			GENERATE = "Generate";
			GENERATING = "Generating ";
			VIDEO_SUCCESS = "Video generated successfully";
			WIDTH = "Width";
			HEIGHT = "Height";
			RESIZE_SUCCESS = "Resizing generated successfully";
			RESIZE = "Resize";
			PROCESS_LOGS = "Program logs:";
			GENERATE_SUCCES = "Successfully generated!";
			KEYS_SEQUENCE = "Keys sequence:";
			WORDS_SEQUENCE = "Words sequence:";
			GENERAL_INFO = "General information:";
			KEYBOARD_LOG = "Keyboard logs:";
			MIX = "Mix";
			MOUSE_ZOOM = "Mouse zoom";
			MAP = "Mapa";
			FILE_LOGS = "File logs:";
			ACCUMULATE_TRACK = "Accumulate track:";
			MAP_LOGS = "Map logs:";
			MOUSE_LOGS = "Mouse logs:";
			POSITION = "Position";
			BACKGROUND_IMAGES = "Background images:";
			FOREGROUND_IMAGES = "Foreground images:";
			TRANSPARENCY = "Transparency:";
			SIZE = "Size:";
			PERCENT = "(Percent)";
			SMALL = "SMALL";
			MEDIUM = "MEDIUM";
			BIG = "BIG";
			FULLSCREEN = "FULLSCREEN";
			CUSTOM = "CUSTOM";
			TOP_LEFT = "TOP / LEFT";
			TOP_RIGHT = "TOP / RIGHT";
			BOTTOM_LEFT = "BOTTOM / LEFT";
			BOTTOM_RIGHT = "BOTTOM / RIGHT";
			TURN_ON = "Turn On";
			TURN_OFF = "Turn Off";
			WEBCAM_EROR = "Coldn't connect to webcam\nCheck the configuration";
			break;
		case PORTUGUES:
			CLICK_TO_GO = "Clique para ir para a pasta";
			YOU_ARE_CONNECTED_AS = "Voc� est� conectado como:";	
			SELECT_LANGUAGE = "Selecione o idioma:";
			THERES_NO_USER_SELECTED = "N�o h� usu�rio selecionado";
			LOGIN = "Entrar";
			CHANGE_USER = "Trocar Usu�rio";
			ABOUT = "Sobre";
			LANGUAGE_CHANGE_TITLE = "Altera��o de idioma";
			LANGUAGE_CHANGE_WARNING = "O idioma s� ser� alterado na \n"+
					"pr�xima inicializa��o do programa. \n";
			CREATE_USER = "Criar usu�rio";
			INITIALIZING = "Iniciando";
			SETTING_APP_CONFIG = "Definindo configura��es do programa";
			LOADING_CONFIG_FILE = "Carregando arquivo de configura��o";
			LOADING_IMAGES = "Carregando imagens";
			VALIDATING_USER_DATA = "Validando dados dos usu�rio";
			INITIALIZING_WEBCAM = "Inicializando sistema de captura de video";
			INITIALIZING_PROCESS = "Inicializando sistema de captura de processos";
			INITIALIZING_SCREENSHOT = "Inicializando sistema de captura de imagem";
			INITIALIZING_FILECHANGE = "Inicializando sistema de altera��o de arquivos";
			INITIALIZING_HELP = "Inicializando sistema de ajuda";
			INITIALIZING_IO = "Inicializando sistema de comunica��o";
			INITIALIZING_ENCODER = "Inicializando encoder";
			INITIALIZING_INTERFACE = "Inicializando interface";
			LOADING_DEFAULT_CONFIG = "Carregando configura��es padr�es";
			SETTING_FINAL_CONFIG = "Definindo configura��es finais";
			APP_SUCCESS_LOAD = "Programa carregado com sucesso!";
			ARE_YOU_SURE_TO_EXIT = "Voc� tem certeza que deseja sair?";
			EXIT_CONFIRMATION_TITLE = "Confirma��o de sa�da";
			CONFIGURE = "Configurar";
			CONFIGURATION = "CONFIGURA��O";
			ADVANCED = "AVAN�ADO (BETA)";
			MOUSE_TRACK = "Rastro do Mouse";
			KEYBOARD_STATICS = "Estatisticas de Teclado";
			FILE_CHANGE = "Altera��o de Arquivos";
			PROCESS_OPENED = "Programas Abertos";
			IMPORT = "Importar";
			EXPORT = "Exportar";
			SELECT_EXPORT_FOLDER = "Selecione uma pasta para exportar:";
			EXPORTING = "Exportando, aguarde...";
			CAPTURE_TAB = "Captura";
			TREAT_IMAGE_TAB = "Tratar imagens";
			VISUALIZATION_TAB = "Visualiza��es";
			STATICS = "Estat�sticas";
			KEYBOARD = "Teclado";
			FILES = "Arquivos";
			PROCESS = "Programas";
			TYPE_USER = "Digite seu nome de usu�rio";
			NEED_MIN_3_CHARS = "Utilize um m�nimo de 3 caracteres";
			ONLY_CHAR_AND_NUMBERS = "O nome de usuarios deve conter apenas letras e numeros";
			BACK = "Voltar";
			ABOUT_DESC = "Criado como projeto de inicia��o cient�fica por "+
					"Yuri Heupa sob orienta��o de Bruno Campagnolo, a partir de recursos da " + 
					"Funda��o Arauc�ria. Vers�o 1.3";
			VIDEO = "V�deo";
			WARNING_AREA = "QUADRO DE AVISOS";
			WARNING_AREA_EXPAND = "QUADRO DE AVISOS: (Clique para expandir)";
			TIME_CAPTURE_SEC = "Tempo de captura em segundos:";
			SAVE_PATH = "Pasta de Salvamento:";
			SEARCH = "Procurar";
			FILES_MOVED_TITLE = "Arquivos movidos";
			FILES_MOVED_MESSAGE = "Os arquivos foram movidos de";
			TO = "para";
			CONFIRM_CHANGES_TITLE = "Confirmar altera��es";
			CONFIRM_CHANGES_MESSAGE = "Voc� tem altera��es n�o salvas, deseja salvar?";
			SELECT_SAVE_FOLDER = "Selecione uma pasta para salvar:";
			SELECT_FOLDER = "Selecione uma pasta:";
			CAMERA_SELECTION = "Sele��o da camera:";
			SAVE = "Salvar";
			CAPTURE_MOUSE_MOVEMENT = "Capturar movimento do mouse:";
			CAPTURE_MOUSE_CLICK = "Capturar cliques do mouse";
			WORDS_TYPED = "Palavras digitadas";
			WORDS_PM = "Palavras por minuto";
			KEYS_TYPED = "Teclas digitadas";
			KEYS_PM = "Teclas por minuto";
			KEYBOARD_SECURITY_WARNING = "ATEN��O! Para sua seguran�a desabilite sempre ao digitar senhas";
			SELECT_WATCH_FOLDER = "Selecione uma pasta para ser monitorada:";
			WATCH_FOLDER = "Pasta sendo monitorada:";
			IMAGES_NUMBER = "N�mero imagens:";
			RESOLUTION = "Resolu��o:";
			FOLDER = "Pasta:";
			LAST_IMAGE = "�ltima imagem:";
			NO_IMAGE_CAPTURED = "Ainda n�o h� imagens capturadas";
			CREATED = "Criados:";
			MODIFIED = "Modificados:";
			DELETED = "Exclu�dos:";
			DATA_SIZE = "Quantidade dados:";
			CLICKS_NUMBER = "N�mero cliques:";
			DISTANCE_TRAVEL = "Dist�ncia percorrida:";
			MORE_USED = "Mais usados:";
			WORDS_NUMBER = "N�mero palavras:";
			LAST_WORDS = "�ltimas palavras:";
			KEYS_NUMBER = "N�mero letras:";
			LAST_KEYS = "�ltimas letras:";
			WORDS_PM_STATIC = "Palavras/Minuto:";
			KEYS_PM_STATIC = "Letras/Minuto:";
			UNDEFINED = "Indefinido";
			IMAGES = "Imagens:";
			FORMAT = "Formato:";
			GENERATE = "Gerar";
			GENERATING = "Gerando ";
			VIDEO_SUCCESS = "Video gerado com sucesso";
			WIDTH = "Largura";
			HEIGHT = "Altura";
			RESIZE_SUCCESS = "Redimensionamento gerado com sucesso";
			RESIZE = "Redimensionar";
			PROCESS_LOGS = "Logs de programas:";
			GENERATE_SUCCES = "Gerado com sucesso!";
			KEYS_SEQUENCE = "Sequencia de teclas:";
			WORDS_SEQUENCE = "Sequencia de palavas:";
			GENERAL_INFO = "Informa��es gerais:";
			KEYBOARD_LOG = "Logs de teclado:";
			MIX = "Misturar";
			MOUSE_ZOOM = "Zoom de mouse";
			MAP = "Mapa";
			FILE_LOGS = "Logs de arquivos:";
			ACCUMULATE_TRACK = "Acumular rastro:";
			MAP_LOGS = "Logs de mapa:";
			MOUSE_LOGS = "Logs de mouse:";
			POSITION = "Posi��o";
			BACKGROUND_IMAGES = "Imagens fundo:";
			FOREGROUND_IMAGES = "Imagens sobrepor:";
			TRANSPARENCY = "Transpar�ncia:";
			SIZE = "Tamanho:";
			PERCENT = "(Porcentagem)";
			SMALL = "PEQUENO";
			MEDIUM = "MEDIO";
			BIG = "GRANDE";
			FULLSCREEN = "TELA INTEIRA";
			CUSTOM = "CUSTOMIZADO";
			TOP_LEFT = "CIMA / ESQUERDA";
			TOP_RIGHT = "CIMA / DIREITA";
			BOTTOM_LEFT = "BAIXO / ESQUERDA";
			BOTTOM_RIGHT = "BAIXO / DIREITA";
			TURN_ON = "Ligar";
			TURN_OFF = "Desligar";
			WEBCAM_EROR = "N�o foi poss�vel conectar com a webcam\nVerifique as configura��es";
			CLICK_ANYWHERE = "(Clique em qualquer lugar para fechar)\n Os m�dulos podem ser ativados aqui abaixo";
			break;

		default:
			break;
		}
	}

	public static String WEBCAM_EROR = "N�o foi poss�vel conectar com a webcam\nVerifique as configura��es";
	public static String YOU_ARE_CONNECTED_AS = "Voc� est� conectado como:";
	public static String SELECT_LANGUAGE = "Selecione o idioma:";
	public static String THERES_NO_USER_SELECTED = "N�o h� usu�rio selecionado";
	public static String LOGIN = "Entrar";
	public static String CHANGE_USER = "Trocar Usu�rio";
	public static String ABOUT = "Sobre";
	public static String LANGUAGE_CHANGE_TITLE = "Altera��o de idioma";
	public static String LANGUAGE_CHANGE_WARNING = "O idioma s� ser� alterado na \n"+
			"pr�xima inicializa��o do programa. \n";
	public static String CREATE_USER = "Criar usu�rio";
	public static String INITIALIZING = "Iniciando";
	public static String SETTING_APP_CONFIG = "Definindo configura��es do programa";
	public static String LOADING_CONFIG_FILE = "Carregando arquivo de configura��o";
	public static String LOADING_IMAGES = "Carregando imagens";
	public static String VALIDATING_USER_DATA = "Validando dados dos usu�rio";
	public static String INITIALIZING_WEBCAM = "Inicializando sistema de captura de video";
	public static String INITIALIZING_PROCESS = "Inicializando sistema de captura de processos";
	public static String INITIALIZING_SCREENSHOT = "Inicializando sistema de captura de imagem";
	public static String INITIALIZING_FILECHANGE = "Inicializando sistema de altera��o de arquivos";
	public static String INITIALIZING_HELP = "Inicializando sistema de ajuda";
	public static String INITIALIZING_IO = "Inicializando sistema de comunica��o";
	public static String INITIALIZING_ENCODER = "Inicializando encoder";
	public static String INITIALIZING_INTERFACE = "Inicializando interface";
	public static String LOADING_DEFAULT_CONFIG = "Carregando configura��es padr�es";
	public static String SETTING_FINAL_CONFIG = "Definindo configura��es finais";
	public static String APP_SUCCESS_LOAD = "Programa inciado com sucesso!";
	public static String ARE_YOU_SURE_TO_EXIT = "Voc� tem certeza que deseja sair?";
	public static String EXIT_CONFIRMATION_TITLE = "Confirma��o de sa�da";
	public static String CONFIGURE = "Configurar";
	public static String CONFIGURATION = "CONFIGURA��O";
	public static String ADVANCED = "AVAN�ADO (BETA)";
	public static String MOUSE_TRACK = "Rastro do Mouse";
	public static String KEYBOARD_STATICS = "Estatisticas de Teclado";
	public static String FILE_CHANGE = "Altera��o de Arquivos";
	public static String PROCESS_OPENED = "Programas Abertos";
	public static String IMPORT = "Importar";
	public static String EXPORT = "Exportar";
	public static String SELECT_EXPORT_FOLDER = "Selecione uma pasta para exportar:";
	public static String EXPORTING = "Exportando, aguarde...";
	public static String CAPTURE_TAB = "Captura";
	public static String TREAT_IMAGE_TAB = "Tratar imagens";
	public static String VISUALIZATION_TAB = "Visualiza��es";
	public static String STATICS = "Estat�sticas";
	public static String KEYBOARD = "Teclado";
	public static String FILES = "Arquivos";
	public static String PROCESS = "Programas";
	public static String TYPE_USER = "Informe o seu nome";
	public static String NEED_MIN_3_CHARS = "Utilize um m�nimo de 3 caracteres";
	public static String ONLY_CHAR_AND_NUMBERS = "O nome de usuarios deve conter apenas letras e numeros";
	public static String BACK = "Voltar";
	public static String ABOUT_DESC = "Criado como projeto de inicia��o cient�fica por "+
			"Yuri Heupa sob orienta��o de Bruno Campagnolo, a partir de recursos da " + 
			"Funda��o Arauc�ria. Vers�o 1.3";
	public static String VIDEO = "V�deo";
	public static String WARNING_AREA = "QUADRO DE AVISOS";
	public static String WARNING_AREA_EXPAND = "QUADRO DE AVISOS: (Clique para expandir)";
	public static String TIME_CAPTURE_SEC = "Tempo de captura em segundos:";
	public static String SAVE_PATH = "Pasta de Salvamento:";
	public static String SEARCH = "Procurar";
	public static String FILES_MOVED_TITLE = "Arquivos movidos";
	public static String FILES_MOVED_MESSAGE = "Os arquivos foram movidos de";
	public static String TO = "para";
	public static String CONFIRM_CHANGES_TITLE = "Confirmar altera��es";
	public static String CONFIRM_CHANGES_MESSAGE = "Voc� tem altera��es n�o salvas, deseja salvar?";
	public static String SELECT_SAVE_FOLDER = "Selecione uma pasta para salvar:";
	public static String SELECT_FOLDER = "Selecione uma pasta:";
	public static String CAMERA_SELECTION = "Sele��o da camera:";
	public static String SAVE = "Salvar";
	public static String CAPTURE_MOUSE_MOVEMENT = "Capturar movimento do mouse:";
	public static String CAPTURE_MOUSE_CLICK = "Capturar cliques do mouse";
	public static String WORDS_TYPED = "Palavras digitadas";
	public static String WORDS_PM = "Palavras por minuto";
	public static String KEYS_TYPED = "Teclas digitadas";
	public static String KEYS_PM = "Teclas por minuto";
	public static String KEYBOARD_SECURITY_WARNING = "ATEN��O! Para sua seguran�a desabilite sempre ao digitar senhas";
	public static String SELECT_WATCH_FOLDER = "Selecione uma pasta para ser monitorada:";
	public static String WATCH_FOLDER = "Pasta sendo monitorada:";
	public static String IMAGES_NUMBER = "N�mero imagens:";
	public static String RESOLUTION = "Resolu��o:";
	public static String FOLDER = "Pasta:";
	public static String LAST_IMAGE = "�ltima imagem:";
	public static String NO_IMAGE_CAPTURED = "Ainda n�o h� imagens capturadas";
	public static String CREATED = "Criados:";
	public static String MODIFIED = "Modificados:";
	public static String DELETED = "Exclu�dos:";
	public static String DATA_SIZE = "Quantidade dados:";
	public static String CLICKS_NUMBER = "N�mero cliques:";
	public static String DISTANCE_TRAVEL = "Dist�ncia percorrida:";
	public static String MORE_USED = "Mais usados:";
	public static String WORDS_NUMBER = "N�mero palavras:";
	public static String LAST_WORDS = "�ltimas palavras:";
	public static String KEYS_NUMBER = "N�mero letras:";
	public static String LAST_KEYS = "�ltimas letras:";
	public static String WORDS_PM_STATIC = "Palavras/Minuto:";
	public static String KEYS_PM_STATIC = "Letras/Minuto:";
	public static String UNDEFINED = "Indefinido";
	public static String IMAGES = "Imagens:";
	public static String FORMAT = "Formato:";
	public static String GENERATE = "Gerar";
	public static String GENERATING = "Gerando ";
	public static String VIDEO_SUCCESS = "Video gerado com sucesso";
	public static String WIDTH = "Largura";
	public static String HEIGHT = "Altura";
	public static String RESIZE_SUCCESS = "Redimensionamento gerado com sucesso";
	public static String RESIZE = "Redimensionar";
	public static String PROCESS_LOGS = "Logs de programas:";
	public static String GENERATE_SUCCES = "Gerado com sucesso!";
	public static String KEYS_SEQUENCE = "Sequencia de teclas:";
	public static String WORDS_SEQUENCE = "Sequencia de palavas:";
	public static String GENERAL_INFO = "Informa��es gerais:";
	public static String KEYBOARD_LOG = "Logs de teclado:";
	public static String MIX = "Misturar";
	public static String MOUSE_ZOOM = "Zoom de mouse";
	public static String MAP = "Mapa";
	public static String FILE_LOGS = "Logs de arquivos:";
	public static String ACCUMULATE_TRACK = "Acumular rastro:";
	public static String MAP_LOGS = "Logs de mapa:";
	public static String MOUSE_LOGS = "Logs de mouse:";
	public static String POSITION = "Posi��o";
	public static String BACKGROUND_IMAGES = "Imagens fundo:";
	public static String FOREGROUND_IMAGES = "Imagens sobrepor:";
	public static String TRANSPARENCY = "Transpar�ncia:";
	public static String SIZE = "Tamanho:";
	public static String PERCENT = "(Porcentagem)";
	public static String SMALL = "PEQUENO";
	public static String MEDIUM = "MEDIO";
	public static String BIG = "GRANDE";
	public static String FULLSCREEN = "TELA INTEIRA";
	public static String CUSTOM = "CUSTOMIZADO";
	public static String TOP_LEFT = "CIMA / ESQUERDA";
	public static String TOP_RIGHT = "CIMA / DIREITA";
	public static String BOTTOM_LEFT = "BAIXO / ESQUERDA";
	public static String BOTTOM_RIGHT = "BAIXO / DIREITA";
	public static String TURN_ON = "Ligar";
	public static String TURN_OFF = "Desligar";
	public static String CLICK_ANYWHERE = "(Clique em qualquer lugar para fechar)\n Os m�dulos podem ser ativados aqui abaixo";
	public static String CLICK_TO_GO = "Clique para ir para a pasta";





}

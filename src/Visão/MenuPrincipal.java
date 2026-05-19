package Visao;

import Modelo.Usuario;System.out.print("Escolha: ");
opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {

        case 1:
        telaUsuario.cadastrarUsuario();
                    break;

                            case 2:
                            telaUsuario.listarUsuarios();
                    break;

                            case 3:

Usuario gerente = new Usuario(
        "Gerente Padrão",
        "12345678901",
        "gerente@email.com",
        "Gerente",
        "admin",
        "123",
        "Gerente"
);

                    telaProjeto.cadastrarProjeto(gerente);
                    break;

                            case 4:
                            telaProjeto.listarProjetos();
                    break;

                            case 5:
                            telaEquipe.cadastrarEquipe();
                    break;

                            case 6:
                            telaEquipe.listarEquipes();
                    break;

                            case 7:

Usuario responsavel = new Usuario(
        "Colaborador",
        "99999999999",
        "colaborador@email.com",
        "Dev",
        "dev",
        "123",
        "Colaborador"
);

                    telaTarefa.cadastrarTarefa(responsavel);
                    break;

                            case 8:
                            telaTarefa.listarTarefas();
                    break;

                            case 0:
                            System.out.println("Sistema encerrado.");
                    break;

default:
        System.out.println("Opção inválida.");
            }

                    } while (opcao != 0);
        }
        }
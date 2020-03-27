package com.taurus.util;

/**
 * Classe con listas de constantes
 *
 * @author Diego Lima
 */
public class ListaConstantesBase {

    public enum Operacao {
        NOVO, EDITAR, EXCLUIR, LEITURA;
    }

    public enum SimNao {
        SIM("sim"), NAO("não");
        private String descricao;

        private SimNao(String desc) {
            this.descricao = desc;
        }

        public String getDescricao() {
            return this.descricao;
        }

        public static SimNao getSimNao(int codigo) {
            for (SimNao sn : SimNao.values()) {
                if (sn.ordinal() == codigo) {
                    return sn;
                }
            }
            return null;
        }
    }

    public enum Sexo {
        MASCULINO("Masculino", "M"), FEMININO("Feminino", "F"),
        NAO_SE_APLICA("Não se aplica", "NA");
        private String descricao;
        private String sigla;

        private Sexo(String desc, String sigla) {
            this.descricao = desc;
            this.sigla = sigla;
        }

        public String getDescricao() {
            return this.descricao;
        }

        public String getSigla() {
            return sigla;
        }

        public static Sexo getSexo(int codigo) {
            for (Sexo sexo : Sexo.values()) {
                if (sexo.ordinal() == codigo) {
                    return sexo;
                }
            }
            return null;
        }
    }

    public enum EstadoCivil {
        SOLTEIRO(0, "Solteiro(a)"),
        CASADO(1, "Casado(a)"),
        DIVORCIADO(2, "Divorciado(a)"),
        VIUVO(3, "Viúvo(a)"),
        SEPARADO(4, "Separado(a)"),
        UNIAO_ESTAVEL(5, "União estável");

        private final int codigo;
        private final String descricao;

        private EstadoCivil(int codigo, String desc) {
            this.codigo = codigo;
            this.descricao = desc;
        }

        public int getCodigo() {
            return codigo;
        }

        public String getDescricao() {
            return descricao;
        }

        public static EstadoCivil getEstadoCivil(int codigo) {
            for (EstadoCivil ec : EstadoCivil.values()) {
                if (ec.getCodigo() == codigo) {
                    return ec;
                }
            }
            return null;
        }
    }

    public enum EstadoBrasil {
        ACRE("Acre", "AC"),
        ALAGOAS("Alagoas", "AL"),
        AMAPA("Amapá", "AP"),
        AMAZONAS("Amazonas", "AM"),
        BAHIA("Bahia", "BA"),
        CEARA("Ceará", "CE"),
        DISTRITO_FEDERAL("Distrito Federal", "DF"),
        ESPIRITO_SANTO("Espírito Santo", "ES"),
        GOIAS("Goiás", "GO"),
        MARANHAO("Maranhão", "MA"),
        MATO_GROSSO("Mato Grosso", "MT"),
        MATO_GROSSO_SUL("Mato Grosso do Sul", "MS"),
        MINAS_GERAIS("Minas Gerais", "MG"),
        PARA("Pará", "PA"),
        PARAIBA("Paraíba", "PB"),
        PARANA("Paraná", "PR"),
        PERNAMBUCO("Pernambuco", "PE"),
        PIAUI("Piauí", "PI"),
        RIO_JANEIRO("Rio de Janeiro", "RJ"),
        RIO_GRANDE_NORTE("Rio Grande do Norte", "RN"),
        RIO_GRANDE_SUL("Rio Grande do Sul", "RS"),
        RONDONIA("Rondônia", "RO"),
        RORAIMA("Roraima", "RR"),
        SANTA_CATARINA("Santa Catarina", "SC"),
        SAO_PAULO("São Paulo", "SP"),
        SERGIPE("Sergipe", "SE"),
        TOCANTINS("Tocantins", "TO");

        private final String nome;
        private final String sigla;

        private EstadoBrasil(String nome, String sigla) {
            this.nome = nome;
            this.sigla = sigla;
        }

        public String getNome() {
            return nome;
        }

        public String getSigla() {
            return sigla;
        }

        public static EstadoBrasil getEstado(String sigla) {
            for (EstadoBrasil estadoBrasil : EstadoBrasil.values()) {
                if (estadoBrasil.getSigla().equals(sigla)) {
                    return estadoBrasil;
                }
            }
            return null;
        }
    }

    public enum EstiloCss {
        BOTAO_LIXEIRA("button-lixeira"), BOTAO_EXCLUIR("button-excluir"), BOTAO_ADICIONAR("button-adicionar"),
        BOTAO_PESQUISAR("button-pesquisar"), BOTAO_PESQUISAR_24("button-pesquisar_24"),
        BOTAO_FECHAR("button-fechar"), BOTAO_NOVO("button-novo"),
        BOTAO_EDITAR("button-editar"), BOTAO_CANCELAR_EDITAR("button-cancelar-editar"),
        BOTAO_TROCAR("button-trocar"), BOTAO_DUVIDA("button-duvida"),
        BOTAO_OK("button-ok"), BOTAO_FALHA("button-falha"), BOTAO_PRINT("button-print"),
        BOTAO_VIEW("button-view"),
        LABEL_LEITURA("label-leitura"),
        TEXTO_VERMELHO("texto-vermelho"), TEXTO_AZUL("texto-azul"), TEXTO_VERDE("texto-verde"), TEXTO_NEGRITO("texto-negrito"),
        TAB_COL_CENTRO("table-column-centro"), TAB_COL_ESQUERDO("table-column-esquerdo"), TAB_COL_DIREITO("table-column-direito"),
        TOOLTIP_VALIDACAO("tooltip-validacao"), CAMPO_OBRIGATORIO("campo-obrigatorio");

        private final String valor;

        private EstiloCss(String valor) {
            this.valor = valor;
        }

        public String getValor() {
            return valor;
        }
    }
}

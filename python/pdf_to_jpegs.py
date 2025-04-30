import PyPDF2
import os
import sys


def split_pdf(input_pdf, output_folder, batch_size=20):
    print("start convert_jpeg_to_pdf from python")
    if not os.path.exists(output_folder):
        os.makedirs(output_folder)

    try:
        with open(input_pdf, "rb") as file:
            reader = PyPDF2.PdfReader(file)
            total_pages = len(reader.pages)

            for start_page in range(0, total_pages, batch_size):
                end_page = min(start_page + batch_size, total_pages)
                for page_num in range(start_page, end_page):
                    try:
                        writer = PyPDF2.PdfWriter()
                        writer.add_page(reader.pages[page_num])

                        output_pdf = os.path.join(output_folder, f"page_{page_num + 1}.pdf")
                        with open(output_pdf, "wb") as output_file:
                            writer.write(output_file)
                    except Exception as e:
                        print(f"Error processing page {page_num + 1}: {e}")
    except FileNotFoundError:
        print(f"Error: File {input_pdf} not found.")
    except Exception as e:
        print(f"An error occurred: {e}")


if __name__ == "__main__":
    try:
        pdfFilePath = sys.argv[1]
        main_root = sys.argv[2]
        split_pdf(pdfFilePath, main_root)
    except IndexError:
        print("Usage: python split_pdf.py <input_pdf> <output_folder>")
    except Exception as e:
        print(f"An error occurred: {e}")
